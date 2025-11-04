package cellularSim;

import gui.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.awt.Color;

public class GameOfLife {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);

        HashSet<Point2D.Double> aliveCells = new HashSet<>();

        // This is a glider, let it fly accros the map
        // It's like a little nomad :D
        aliveCells.add(new Point2D.Double(1, 0));
        aliveCells.add(new Point2D.Double(2, 1));
        aliveCells.add(new Point2D.Double(0, 2));
        aliveCells.add(new Point2D.Double(1, 2));
        aliveCells.add(new Point2D.Double(2, 2));

        // This is a square with 11 eleven generations before being stable
        // It even settle into 4 colonies :O
        for (int i = 10; i < 15; i++){
            for (int j = 10; j < 15; j++){
                aliveCells.add(new Point2D.Double(i, j));
            }
        }

        SimBoard game = new SimBoard(gui, aliveCells, 10, 50, 50, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY);
    }
}
