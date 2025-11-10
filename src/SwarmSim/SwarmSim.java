package SwarmSim;

import gui.GUISimulator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SwarmSim {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);


        List<Boids> boidsList = new ArrayList<>();
        boidsList.add(new Boids(150, 150, 1, 5*(3.14/4.0),Color.GREEN));
        boidsList.add(new Boids(100, 100, 1, 5*(3.14/4.0) ,Color.blue));
        boidsList.add(new Boids(200, 100, 1, 7*(3.14/4.0) ,Color.blue));
        boidsList.add(new Boids(300, 200, 1, 0,Color.blue));

        SwarmBoard sim = new SwarmBoard(gui,boidsList);
    }
}
