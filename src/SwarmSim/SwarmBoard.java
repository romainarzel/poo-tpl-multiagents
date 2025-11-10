package SwarmSim;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SwarmBoard implements Simulable {

    private GUISimulator gui;
    private List<Boids> boids;

    public SwarmBoard(GUISimulator gui,List<Boids> boids){
        this.gui = gui;
        this.boids = boids;

        gui.setSimulable(this);
        gui.reset();
        draw();
    }

    public void draw () {
        gui.reset();
        for (Boids b : boids) {
            gui.addGraphicalElement(b);
        }
    }

    private List<Boids> initListBoidRandom (int nbBoids) {
        Random rng = new Random();
        List<Boids> lb = new ArrayList<>();
        for (int i = 0; i < nbBoids; i++) {
            int x, y ;
            double velocity,direction ;

           x = rng.nextInt(0, gui.getPanelWidth());
           y = rng.nextInt(0, gui.getPanelHeight());

           direction = rng.nextDouble(Math.PI);
           velocity = rng.nextDouble(0.1 , 3);


           lb.add( new Boids(x,y,velocity,direction, Color.PINK));

        }
        return lb;
    }

    @Override
    public void next() {
        for (Boids b : boids){
            b.updateBoids(this.boids);
        }
        for (Boids b : boids){
            b.applyUpdate();
            b.wrapPosition(gui.getWidth(), gui.getHeight());
        }
        draw();

    }

    @Override
    public void restart() {
        this.boids = initListBoidRandom(45);
        draw();

    }
}
