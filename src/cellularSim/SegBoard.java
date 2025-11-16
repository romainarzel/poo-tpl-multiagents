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
                    cellColor = linearColorGradient(minStateCellColor, maxStateCellColor, (float) getPercent(lig, col) / 100);
                }

                gui.addGraphicalElement(new gui.Rectangle(
                        20 + cellSize * col,
                        20 + cellSize * lig,
                        bgColor, cellColor, cellSize));

            }
        }
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
