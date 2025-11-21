package SwarmSim;

import gui.GraphicalElement;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

/**
 * Représente un boid (bird-oid object) dans une simulation d'essaim.
 * 
 * <p>
 * Cette classe implémente l'algorithme des boids de Craig Reynolds, qui simule
 * le comportement collectif d'essaims, de bancs de poissons ou de volées
 * d'oiseaux.
 * </p>
 * 
 * <p>
 * Les boids suivent trois règles fondamentales :
 * <ul>
 * <li><b>Séparation</b> : éviter l'encombrement avec les voisins proches</li>
 * <li><b>Alignement</b> : adopter la direction et vitesse moyennes des
 * voisins</li>
 * <li><b>Cohésion</b> : se déplacer vers le centre de masse des voisins</li>
 * </ul>
 * En plus de ces règles, les boids évitent également les bords de la fenêtre.
 * </p>
 * 
 * <p>
 * L'implémentation utilise un double buffering pour la mise à jour de l'état :
 * {@link #updateBoids(List)} calcule le prochain état sans le modifier
 * immédiatement,
 * puis {@link #applyUpdate()} applique les changements. Cela évite les
 * incohérences
 * lors des mises à jour simultanées de plusieurs boids.
 * </p>
 * 
 * <p>
 * Les boids sont représentés graphiquement par des triangles colorés orientés
 * dans la direction de leur mouvement.
 * </p>
 * 
 * @see Element
 * @see Swarm
 * @see SwarmSimulator
 */
public class Boids extends Element implements GraphicalElement {

    private double size;
    private Color color;

    // wall to avoid
    private int windowWidth;
    private int windowHeight;

    // Double-buffered future state
    protected double nextDirection;
    protected double nextVelocity;

    /**
     * Distance des bords de la fenêtre à partir de laquelle le boid commence à
     * tourner
     */
    private static final double WALL_MARGIN = 30.0;

    /**
     * Intensité de la force qui repousse les boids des murs (0.0 = aucune, 1.0 =
     * maximale)
     */
    private static final double WALL_TURN_STRENGTH = 0.85;

    /** Rayon de détection des voisins pour l'alignement et la cohésion */
    private static final double NEIGHBOR_RADIUS = 150;

    /** Rayon de séparation - les boids trop proches se repoussent */
    private static final double SEPARATION_RADIUS = 40;

    /** Force de la répulsion entre boids proches */
    private static final double SEPARATION_STRENGTH = 1.75;

    /** Force d'attraction vers le centre de masse des voisins */
    private static final double COHESION_STRENGTH = 0.0005;

    /** Vitesse maximale d'un boid */
    private static final double MAX_VELOCITY = 3.5;

    /** Vitesse minimale d'un boid */
    private static final double MIN_VELOCITY = 0.5;

    /** Taille graphique du triangle représentant le boid */
    private static final double BOIDS_SIZE = 20;

    /**
     * Crée un nouveau boid avec les paramètres spécifiés.
     * 
     * @param x         position initiale en x
     * @param y         position initiale en y
     * @param velocity  vitesse initiale (sera limitée entre MIN_VELOCITY et
     *                  MAX_VELOCITY)
     * @param direction direction initiale en radians (0 = haut, sens horaire)
     * @param color     couleur du boid pour l'affichage
     * @param wallX     largeur de la zone de simulation
     * @param wallY     hauteur de lazone de simulation
     * @throws IllegalArgumentException si wallX ou wallY sont inférieurs ou égaux à
     *                                  0
     */
    public Boids(int x, int y, double velocity, double direction, Color color, int wallX, int wallY) {

        if (wallX <= 0 || wallY <= 0) {
            throw new IllegalArgumentException("Wall cannot be inferior to 1");
        }
        this.windowWidth = wallX;
        this.windowHeight = wallY;

        System.out.println("Screen for boid x: " + wallX + " y: " + wallY);
        this.x = clamp(x, 0, wallX);
        this.y = clamp(y, 0, wallY);
        this.velocity = clamp(velocity, MIN_VELOCITY, MAX_VELOCITY);
        this.direction = direction % Math.PI;
        this.size = BOIDS_SIZE;
        this.color = color;
    }

    /**
     * Enroule la position du boid autour de la fenêtre (comportement toroïdal).
     * 
     * <p>
     * Si le boid sort de la fenêtre par un côté, il réapparaît de l'autre côté.
     * Actuellement non utilisé (les boids utilisent plutôt
     * {@link #wallAvoidance()}).
     * </p>
     * 
     * @param width  largeur de la fenêtre
     * @param height hauteur de la fenêtre
     */
    public void wrapPosition(int width, int height) {
        if (x < 0)
            x += width;
        if (x >= width)
            x -= width;

        if (y < 0)
            y += height;
        if (y >= height)
            y -= height;
    }

    /**
     * Calcule la prochaine direction et vitesse du boid selon les règles de
     * comportement collectif.
     * 
     * <p>
     * Cette méthode implémente les trois règles fondamentales des boids :
     * <ul>
     * <li><b>Alignement</b> : le boid adapte sa direction et sa vitesse à la
     * moyenne pondérée
     * de ses voisins (les voisins plus proches ont plus de poids)</li>
     * <li><b>Séparation</b> : le boid s'éloigne des voisins trop proches pour
     * éviter les collisions</li>
     * <li><b>Cohésion</b> : le boid se dirige vers le centre de masse de ses
     * voisins</li>
     * </ul>
     * </p>
     * 
     * <p>
     * La méthode ajoute également l'évitement des murs via
     * {@link #wallAvoidance()}.
     * Les nouvelles valeurs sont stockées dans {@link #nextDirection} et
     * {@link #nextVelocity}
     * et ne sont appliquées que lors de l'appel à {@link #applyUpdate()}.
     * </p>
     * 
     * @param all liste de tous les boids de la simulation (inclut ce boid)
     */
    public void updateBoids(List<Boids> all) {

        // ---- Collect neighbors ----
        List<Boids> neighbors = new LinkedList<>();
        for (Boids b : all) {
            if (b == this)
                continue;
            if (this.distance(b) < NEIGHBOR_RADIUS) {
                neighbors.add(b);
            }
        }

        if (neighbors.isEmpty()) {
            nextDirection = this.direction;
            nextVelocity = this.velocity;
            return;
        }

        // Keep same speed unless alignment changes it
        nextVelocity = this.velocity;

        // WEIGHTED ALIGNMENT (direction + velocity)
        double sumVx = this.velocity * Math.sin(this.direction); // weight = 1 for self
        double sumVy = this.velocity * -Math.cos(this.direction);
        double totalWeight = 1.0;

        for (Boids b : neighbors) {
            double dist = this.distance(b);
            double weight = 1.0 / (dist + 0.0001); // closer → stronger

            double bd = b.getDirection();
            double bv = b.getVelocity();

            // Neighbor velocity vector
            double vx = bv * Math.sin(bd);
            double vy = bv * -Math.cos(bd);

            sumVx += vx * weight;
            sumVy += vy * weight;
            totalWeight += weight;
        }

        // Average velocity vector from alignment
        double avgVx = sumVx / totalWeight;
        double avgVy = sumVy / totalWeight;

        // SEPARATION (repulsion when too close)
        double sepX = 0;
        double sepY = 0;

        for (Boids b : neighbors) {
            double dist = this.distance(b);

            if (dist < SEPARATION_RADIUS) {
                // push away from neighbor
                double dx = this.x - b.x;
                double dy = this.y - b.y;

                double inv = 1.0 / (dist + 0.0001);
                dx *= inv;
                dy *= inv;

                // force grows when distance gets smaller
                double force = (SEPARATION_RADIUS - dist) / SEPARATION_RADIUS;

                sepX += dx * force;
                sepY += dy * force;
            }
        }

        // Apply separation influence
        avgVx += sepX * SEPARATION_STRENGTH;
        avgVy += sepY * SEPARATION_STRENGTH;

        // COHESION (move toward center of neighbors)
        double comX = 0;
        double comY = 0;
        totalWeight = 0;

        for (Boids b : neighbors) {
            double dist = this.distance(b);
            double w = 1.0 / (dist + 0.0001);

            comX += b.x * w;
            comY += b.y * w;
            totalWeight += w;
        }

        comX /= totalWeight;
        comY /= totalWeight;

        // vector toward center of mass
        double cohX = (comX - this.x) * COHESION_STRENGTH;
        double cohY = (comY - this.y) * COHESION_STRENGTH;

        avgVx += cohX;
        avgVy += cohY;

        // Wall avoidance
        Point2D.Double wall = wallAvoidance();
        avgVx += wall.x;
        avgVy += wall.y;

        nextVelocity = Math.sqrt(avgVx * avgVx + avgVy * avgVy);
        if (nextVelocity < MIN_VELOCITY) {
            nextVelocity = MIN_VELOCITY;
        }
        if (nextVelocity > MAX_VELOCITY) {
            nextVelocity = MAX_VELOCITY;
        }
        nextDirection = Math.atan2(avgVx, -avgVy);
    }

    /**
     * Applique les valeurs de direction et vitesse calculées précédemment.
     * 
     * <p>
     * Cette méthode applique les valeurs stockées dans {@link #nextDirection}
     * et {@link #nextVelocity}, puis met à jour la position du boid en fonction
     * de sa nouvelle vitesse et direction.
     * </p>
     * 
     * @see #updateBoids(List)
     */
    public void applyUpdate() {
        this.direction = nextDirection;
        this.velocity = nextVelocity;
        this.x += velocity * Math.sin(direction);
        this.y -= velocity * Math.cos(direction);
    }

    /**
     * Dessine le boid sur le contexte graphique.
     * 
     * <p>
     * Le boid est représenté par un triangle coloré orienté dans la direction
     * de son mouvement. La pointe du triangle indique la direction avant.
     * </p>
     * 
     * @param g2 le contexte graphique 2D pour le dessin
     */
    @Override
    public void paint(Graphics2D g2) {
        Path2D.Double triangle = new Path2D.Double();
        double halfBase = size * 0.5;

        // Triangle pointing “up” by default, tip at origin
        triangle.moveTo(0, -size);
        triangle.lineTo(halfBase, 0);
        triangle.lineTo(-halfBase, 0);
        triangle.closePath();

        Graphics2D g = (Graphics2D) g2.create();
        try {
            g.setColor(this.color);
            AffineTransform at = new AffineTransform();
            at.translate(x, y); // move to boid position
            at.rotate(direction); // rotate to boid heading
            g.transform(at);
            g.fill(triangle); // filled triangle (use draw() if you only want outline)
        } finally {
            g.dispose();
        }
    }

    /**
     * Calcule la force d'évitement des murs pour ce boid.
     * 
     * <p>
     * Lorsqu'un boid s'approche trop près d'un mur (à moins de {@link #WALL_MARGIN}
     * pixels),
     * une force de répulsion proportionnelle à la proximité est générée pour le
     * faire tourner.
     * L'intensité de cette force est contrôlée par {@link #WALL_TURN_STRENGTH}.
     * </p>
     * 
     * @return un vecteur force (Point2D.Double) à ajouter à la vélocité du boid
     */
    private Point2D.Double wallAvoidance() {
        double fx = 0;
        double fy = 0;

        // Left wall
        if (x < WALL_MARGIN) {
            fx += (WALL_MARGIN - x) / WALL_MARGIN;
        }
        // Right wall
        if (x > windowWidth - WALL_MARGIN) {
            fx -= (x - (windowWidth - WALL_MARGIN)) / WALL_MARGIN;
        }

        // Top wall
        if (y < WALL_MARGIN) {
            fy += (WALL_MARGIN - y) / WALL_MARGIN;
        }
        // Bottom wall
        if (y > windowHeight - WALL_MARGIN) {
            fy -= (y - (windowHeight - WALL_MARGIN)) / WALL_MARGIN;
        }

        // Apply tuning strength
        fx *= WALL_TURN_STRENGTH;
        fy *= WALL_TURN_STRENGTH;

        return new Point2D.Double(fx, fy);
    }

    /**
     * Limite une valeur entière entre un minimum et un maximum.
     * 
     * @param value la valeur à limiter
     * @param min   la valeur minimale
     * @param max   la valeur maximale
     * @return la valeur limitée à l'intervalle [min, max]
     */
    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    /**
     * Limite une valeur double entre un minimum et un maximum.
     * 
     * @param value la valeur à limiter
     * @param min   la valeur minimale
     * @param max   la valeur maximale
     * @return la valeur limitée à l'intervalle [min, max]
     */
    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }

}
