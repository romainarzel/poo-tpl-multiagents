package cellularSim;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

class SegBoard extends Board implements Simulable {
    private final GUISimulator gui;
    private final int cellSize;
    private final Color minStateCellColor;
    private final Color maxStateCellColor;
    private final Color deadCellColor;
    private final Color bgColor;
    private final HashMap<Point2D.Double, Integer> starter;
    private final int maxState;
    private final int segSeuil;

    public SegBoard(GUISimulator gui, HashMap<Point2D.Double, Integer> starter, int maxState, int segSeuil, int cellSize, int width, int height,Color minStateCellColor, Color maxStateCellColor, Color deadCellColor, Color bgColor){
        super(width, height, starter, maxState, segSeuil);
        this.gui = gui;
        gui.setSimulable(this);

        this.cellSize = cellSize;
        this.starter = starter;
        this.maxState = maxState;
        this.segSeuil = segSeuil;
        this.maxStateCellColor = maxStateCellColor;
        this.minStateCellColor = minStateCellColor;
        this.deadCellColor = deadCellColor;
        this.bgColor = bgColor;

        draw();
    }

    public void draw(){
        gui.reset();

        Color cellColor;
        for (int col = 0; col < getWidth(); col++){

            for (int lig = 0; lig < getHeight(); lig++){
                if (getPercent(lig, col) == 0){
                    cellColor = deadCellColor;
                } else {
                    cellColor = linearColorGradient(minStateCellColor, maxStateCellColor, getPercent(lig, col));
                }

                gui.addGraphicalElement(new gui.Rectangle(
                        20 + cellSize * col,
                        20 + cellSize * lig,
                        bgColor, cellColor, cellSize));

            }
        }
    }

    private Color linearColorGradient(Color c1, Color c2, float percent){
        if (percent > 100 || percent < 0){
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
        nextGenSeg();
        draw();
    }

    public void restart(){
        setCellBoard(starter, maxState, segSeuil);
        draw();
    }
}
