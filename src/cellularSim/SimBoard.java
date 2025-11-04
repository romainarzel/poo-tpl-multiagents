package cellularSim;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;

class SimBoard extends Board implements Simulable {
    private final GUISimulator gui;
    private final int cellSize;
    private final Color maxStateCellColor;
    private final Color deadCellColor;
    private final Color bgColor;
    private final HashSet<Point2D.Double> starter;

    public SimBoard(GUISimulator gui, HashSet<Point2D.Double> starter, int cellSize, int width, int height, Color maxStateCellColor, Color deadCellColor, Color bgColor){
        super(width, height, starter);
        this.gui = gui;
        gui.setSimulable(this);

        this.cellSize = cellSize;
        this.starter = starter;
        this.maxStateCellColor = maxStateCellColor;
        this.deadCellColor = deadCellColor;
        this.bgColor = bgColor;

        draw();
    }

    public void draw(){
        Color cellColor;
        for (int x = 0; x < getWidth(); x++){

            for (int y = 0; y < getHeight(); y++){
                cellColor = linearColorGradient(deadCellColor, maxStateCellColor, getPercent(y, x));

                gui.addGraphicalElement(new gui.Rectangle(
                        20 + cellSize * x,
                        20 + cellSize * y,
                        bgColor, cellColor, cellSize));

            }
        }
    }

    private Color linearColorGradient(Color c1, Color c2, float percent){
        if (percent > 100 | percent < 0){
            throw new IllegalArgumentException("percentage is out of range");
        }

        int r1 = c1.getRed(); int g1 = c1.getGreen(); int b1 = c1.getBlue();
        int r2 = c2.getRed(); int g2 = c2.getGreen(); int b2 = c2.getBlue();

        int newRed = (int)(r1 + percent * (r2 - r1));
        int newGreen = (int)(g1 + percent * (g2 - g1));
        int newBlue = (int)(b1 + percent * (b2 - b1));

        return new Color(newRed, newGreen, newBlue);
    }

    public void next(){
        nextGenConway();
        gui.reset();
        draw();
    }

    public void restart(){
        setCellBoard(starter);
        gui.reset();
        draw();
    }
}
