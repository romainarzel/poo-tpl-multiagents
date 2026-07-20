package SwarmSim;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;

/**
 * Simulator managing multiple boid groups with a discrete event scheduler.
 */
public class SwarmSimulator implements Simulable {

    private final GUISimulator gui;
    private final EventManager manager = new EventManager();

    // Groups modeled via reusable Swarm classes
    private Swarm sardineSwarm;
    private PredatorSwarm predatorSwarm;

    public SwarmSimulator(GUISimulator gui, int nbBoids) {
        this.gui = gui;
        gui.setSimulable(this);
        initGroups(nbBoids, gui.getPanelWidth(), gui.getPanelHeight());

        // Schedule each group at its own pace
        manager.addEvent(new SardineStep(manager.getCurrentDate() + 1));      // fast group
        manager.addEvent(new PredatorStep(manager.getCurrentDate() + 2));     // slower group
        drawAll();
    }

    private void initGroups(int nbBoids, int width, int height) {
        sardineSwarm = new Swarm(nbBoids) {
            @Override
            protected Boids createBoid(int x, int y, double velocity, double direction, int width, int height) {
                return new Boids(x, y, velocity, direction, Color.CYAN, width, height);
            }
        };
        sardineSwarm.reInit(width, height);

        int nbPred = Math.max(1, nbBoids / 6);
        predatorSwarm = new PredatorSwarm(nbPred);
        predatorSwarm.reInit(width, height);
    }

    private void drawAll() {
        gui.reset();
        for (Boids b : sardineSwarm.getBoids()) gui.addGraphicalElement(b);
        for (Boids b : predatorSwarm.getBoids()) gui.addGraphicalElement(b);
    }

    @Override
    public void next() {
        manager.next();
    }

    @Override
    public void restart() {
        manager.restart();
        int total = sardineSwarm.getNbBoids() + predatorSwarm.getNbBoids();
        initGroups(total, gui.getPanelWidth(), gui.getPanelHeight());
        manager.addEvent(new SardineStep(manager.getCurrentDate() + 1));
        manager.addEvent(new PredatorStep(manager.getCurrentDate() + 2));
        drawAll();
    }

    // Event for sardines (baseline flocking), runs every tick
    private class SardineStep extends Event {
        public SardineStep(long date) { super(date); }
        @Override public void execute() {
            sardineSwarm.update();
            int w = gui.getPanelWidth(), h = gui.getPanelHeight();
            for (Boids b : sardineSwarm.getBoids()) b.wrapPosition(w, h);
            drawAll();
            manager.addEvent(new SardineStep(getDate() + 1));
        }
    }

    // Event for predators: pursues sardines, runs every 2 ticks
    private class PredatorStep extends Event {
        public PredatorStep(long date) { super(date); }
        @Override public void execute() {
            // Set predator targets to current sardines
            java.util.List<Boids> sardines = sardineSwarm.getBoids();
            for (Boids b : predatorSwarm.getBoids()) {
                if (b instanceof PredatorBoid pb) {
                    pb.setTargets(sardines);
                }
            }
            predatorSwarm.update();
            int w = gui.getPanelWidth(), h = gui.getPanelHeight();
            for (Boids p : predatorSwarm.getBoids()) p.wrapPosition(w, h);
            drawAll();
            manager.addEvent(new PredatorStep(getDate() + 2));
        }
    }
}
