package SwarmSim;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

/**
 * Variant de {@link Boids} qui comporte des capacit\u00e9s de pr\u00e9dation.
 * 
 * <p>
 * Cette classe \u00e9tend {@link Boids} et ajoute un comportement de chasse qui
 * permet
 * au pr\u00e9dateur de poursuivre une liste de cibles sp\u00e9cifi\u00e9es.
 * </p>
 * \n *
 * <p>
 * Le comportement du pr\u00e9dateur combine :
 * <ul>
 * <li>Les r\u00e8gles normales de boids (s\u00e9paration, alignement,
 * coh\u00e9sion) via {@code super.updateBoids()}</li>
 * <li>Un comportement de chasse qui oriente le pr\u00e9dateur vers la cible la
 * plus proche</li>
 * <li>Une acc\u00e9l\u00e9ration pour atteindre des vitesses de pointe lors de
 * la poursuite</li>
 * <li>Un ralentissement lorsqu'il s'approche tr\u00e8s pr\u00e8s d'une
 * cible</li>
 * </ul>
 * </p>
 * \n * @see Boids
 * 
 * @see PredatorSwarm
 */
public class PredatorBoid extends Boids {

    private List<Boids> targets = Collections.emptyList();

    /**
     * Crée un nouveau prédateur boid.
     * 
     * @param x         position initiale en x
     * @param y         position initiale en y
     * @param velocity  vitesse initiale
     * @param direction direction initiale en radians
     * @param color     couleur du prédateur pour l'affichage
     * @param wallX     largeur de la zone de simulation
     * @param wallY     hauteur de la zone de simulation
     */
    public PredatorBoid(int x, int y, double velocity, double direction, Color color, int wallX, int wallY) {
        super(x, y, velocity, direction, color, wallX, wallY);
    }

    /**
     * Définit la liste des cibles à poursuivre.
     * 
     * @param targets liste des boids à considérer comme proies (null sera converti
     *                en liste vide)
     */
    public void setTargets(List<Boids> targets) {
        this.targets = (targets == null) ? Collections.emptyList() : targets;
    }

    /**
     * Met à jour le comportement du prédateur en combinant les règles de boids et
     * la chasse.
     * 
     * <p>
     * Applique d'abord les règles normales des boids via
     * {@code super.updateBoids()},
     * puis ajoute un comportement de poursuite vers la cible la plus proche dans la
     * liste {@link #targets}.
     * Le prédateur mélange progressivement sa direction avec celle vers la cible
     * (alpha = 0.35),
     * accélère pour atteindre la vitesse maximale, et ralentit à l'approche
     * immédiate de sa cible.
     * </p>
     * 
     * @param all liste de tous les boids dans la simulation
     */
    @Override
    public void updateBoids(List<Boids> all) {
        super.updateBoids(all);
        if (targets.isEmpty())
            return;

        Boids nearest = null;
        double best = java.lang.Double.POSITIVE_INFINITY;
        for (Boids t : targets) {
            double d = this.distance(t);
            if (d < best) {
                best = d;
                nearest = t;
            }
        }
        if (nearest == null)
            return;

        double dx = nearest.x - this.x;
        double dy = nearest.y - this.y;
        double desired = Math.atan2(dy, dx);

        double alpha = 0.35;
        this.nextDirection = blendAngles(this.nextDirection, desired, alpha);

        double maxSpeed = 3.5;
        double accel = 0.3;
        this.nextVelocity = Math.min(this.nextVelocity + accel, maxSpeed);

        if (best < 25)
            this.nextVelocity *= 0.9;
    }

    /**
     * Mélange deux angles en interpolant leurs coordonnées cartésiennes.
     * 
     * <p>
     * Convertit les angles en vecteurs unitaires, interpole linéairement ces
     * vecteurs,
     * puis convertit le résultat en angle. Cela évite les problèmes de
     * discontinuité
     * autour de 0/2π radians.
     * </p>
     * 
     * @param a premier angle en radians
     * @param b deuxième angle en radians
     * @param t facteur d'interpolation (0 = angle a, 1 = angle b)
     * @return l'angle interpolé en radians
     */
    private static double blendAngles(double a, double b, double t) {
        double x = (1 - t) * Math.cos(a) + t * Math.cos(b);
        double y = (1 - t) * Math.sin(a) + t * Math.sin(b);
        return Math.atan2(y, x);
    }
}
