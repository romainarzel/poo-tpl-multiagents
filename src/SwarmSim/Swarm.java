package SwarmSim;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Swarm model holding and updating a list of boids.
 * Mirrors the structure used in TestBalls (model + Simulable wrapper).
 */
class Swarm {
    private final int nbBoids;
    private final Random random;
    private List<Boids> boids;

    public Swarm(int nbBoids) {
        this(nbBoids, new Random());
    }

    protected Swarm(int nbBoids, Random random) {
        if (nbBoids < 1) {
            throw new IllegalArgumentException("number of Boids need to be superior to 1");
        }
        this.nbBoids = nbBoids;
        this.random = Objects.requireNonNull(random, "random cannot be null");
        this.boids = new LinkedList<>();
    }

    public int getNbBoids() {
        return nbBoids;
    }

    public List<Boids> getBoids() {
        return boids;
    }

    public void reInit(int width, int height) {
        this.boids = initListBoidRandom(nbBoids, width, height);
    }

    /**
     * Compute next state for all boids, then apply it.
     */
    public void update() {
        for (Boids b : boids) {
            b.updateBoids(this.boids);
        }
        for (Boids b : boids) {
            b.applyUpdate();
        }
    }

    protected List<Boids> initListBoidRandom(int n, int width, int height) {
        List<Boids> lb = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(0, Math.max(1, width));
            int y = random.nextInt(0, Math.max(1, height));

            double direction = random.nextDouble(Math.PI);
            double velocity = random.nextDouble(getMinimumInitialVelocity(), 3);

            lb.add(createBoid(x, y, velocity, direction, width, height));
        }
        return lb;
    }

    protected double getMinimumInitialVelocity() {
        return 0.1;
    }

    protected Boids createBoid(int x, int y, double velocity, double direction, int width, int height) {
        return new Boids(x, y, velocity, direction, Color.PINK, width, height);
    }
}
