package cellularSim;

import gui.GUISimulator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

public class GameOfImmigration {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);

        HashMap<Point2D.Double, Integer> aliveCells = new HashMap<>(21);

        aliveCells.put(new Point2D.Double(10, 10), 3);
        aliveCells.put(new Point2D.Double(12, 10), 1);
        aliveCells.put(new Point2D.Double(13, 10), 1);

        aliveCells.put(new Point2D.Double(10, 11), 3);
        aliveCells.put(new Point2D.Double(11, 11), 1);
        aliveCells.put(new Point2D.Double(12, 11), 1);
        aliveCells.put(new Point2D.Double(13, 11), 1);
        aliveCells.put(new Point2D.Double(14, 11), 2);

        aliveCells.put(new Point2D.Double(10, 12), 1);
        aliveCells.put(new Point2D.Double(11, 12), 1);
        aliveCells.put(new Point2D.Double(12, 12), 3);
        aliveCells.put(new Point2D.Double(13, 12), 2);
        aliveCells.put(new Point2D.Double(14, 12), 2);

        aliveCells.put(new Point2D.Double(11, 13), 1);
        aliveCells.put(new Point2D.Double(12, 13), 2);
        aliveCells.put(new Point2D.Double(13, 13), 2);
        aliveCells.put(new Point2D.Double(14, 13), 2);

        aliveCells.put(new Point2D.Double(11, 14), 3);
        aliveCells.put(new Point2D.Double(12, 14), 2);
        aliveCells.put(new Point2D.Double(13, 14), 2);
        aliveCells.put(new Point2D.Double(14, 14), 1);

        ImmigrationBoard game = new ImmigrationBoard(gui, aliveCells, 3, 10, 50, 50, Color.BLACK, Color.WHITE, Color.LIGHT_GRAY);
    }
}
