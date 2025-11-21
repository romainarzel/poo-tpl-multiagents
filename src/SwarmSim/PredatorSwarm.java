package SwarmSim;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Variante de {@link Swarm} qui crée des instances de {@link PredatorBoid}
 * plutôt que des {@link Boids} standards.
 * 
 * <p>
 * Cette classe surcharge la méthode d'initialisation de la liste de boids pour
 * créer
 * un groupe de prédateurs avec des positions, vitesses et directions
 * aléatoires.
 * Les prédateurs sont affichés en orange pour les distinguer visuellement des
 * proies.
 * </p>
 * 
 * @see PredatorBoid
 * @see Swarm
 */
class PredatorSwarm extends Swarm {

    /**
     * Crée un essaim de prédateurs avec le nombre spécifié de membres.
     * 
     * @param nbBoids nombre de prédateurs dans l'essaim
     */
    public PredatorSwarm(int nbBoids) {
        super(nbBoids);
    }

    /**
     * Initialise une liste de prédateurs boids avec positions et vitesses
     * aléatoires.
     * 
     * <p>
     * Chaque prédateur est créé avec une couleur orange, une position aléatoire,
     * une direction aléatoire et une vitesse comprise entre 0.6 et 3.0.
     * </p>
     * 
     * @param n      nombre de prédateurs à créer
     * @param width  largeur de la zone de simulation
     * @param height hauteur de la zone de simulation
     * @return liste de nouveaux prédateurs boids
     */
    @Override
    protected List<Boids> initListBoidRandom(int n, int width, int height) {
        Random rng = new Random();
        List<Boids> lb = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            int x = rng.nextInt(0, Math.max(1, width));
            int y = rng.nextInt(0, Math.max(1, height));

            double direction = rng.nextDouble(Math.PI);
            double velocity = rng.nextDouble(0.6, 3);

            lb.add(new PredatorBoid(x, y, velocity, direction, Color.ORANGE, width, height));
        }
        return lb;
    }
}
