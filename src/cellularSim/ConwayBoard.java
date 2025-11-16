package cellularSim;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;

class ConwayBoard extends Board implements Simulable {
    private final GUISimulator gui;
    private final int cellSize;
    private final Color maxStateCellColor;
    private final Color deadCellColor;
    private final Color bgColor;
    private final HashSet<Point2D.Double> starter;

    public ConwayBoard(GUISimulator gui, HashSet<Point2D.Double> starter, int cellSize, int width, int height, Color maxStateCellColor, Color deadCellColor, Color bgColor){
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
                cellColor = linearColorGradient(deadCellColor, maxStateCellColor, (float) getPercent(y, x) / 100);

                gui.addGraphicalElement(new gui.Rectangle(
                        20 + cellSize * x,
                        20 + cellSize * y,
                        bgColor, cellColor, cellSize));

            }
        }
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
