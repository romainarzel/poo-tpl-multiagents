package cellularSim;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

class ImmigrationBoard extends Board implements Simulable {
    private final GUISimulator gui;
    private final int cellSize;
    private final Color maxStateCellColor;
    private final Color deadCellColor;
    private final Color bgColor;
    private final HashMap<Point2D.Double, Integer> starter;
    private final int maxState;

    public ImmigrationBoard(GUISimulator gui, HashMap<Point2D.Double, Integer> starter, int maxState, int cellSize, int width, int height, Color maxStateCellColor, Color deadCellColor, Color bgColor){
        super(width, height, starter, maxState);
        this.gui = gui;
        gui.setSimulable(this);

        this.cellSize = cellSize;
        this.starter = starter;
        this.maxState = maxState;
        this.maxStateCellColor = maxStateCellColor;
        this.deadCellColor = deadCellColor;
        this.bgColor = bgColor;

        draw();
    }

    public void draw(){
        gui.reset();

        Color cellColor;
        for (int col = 0; col < getWidth(); col++){

            for (int lig = 0; lig < getHeight(); lig++){
                cellColor = linearColorGradient(deadCellColor, maxStateCellColor, (float) getPercent(lig, col) / 100);

                gui.addGraphicalElement(new gui.Rectangle(
                        20 + cellSize * col,
                        20 + cellSize * lig,
                        bgColor, cellColor, cellSize));

            }
        }
    }

    public void next(){
        nextGenImmigration();
        draw();
    }

    public void restart(){
        setCellBoard(starter, maxState);
        draw();
    }
}
