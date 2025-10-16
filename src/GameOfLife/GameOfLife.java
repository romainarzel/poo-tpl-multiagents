package GameOfLife;

import gui.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.awt.Color;

public class GameOfLife {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);

        HashSet<Point2D.Double> aliveCells = new HashSet<>();
        aliveCells.add(new Point2D.Double(12, 10));
        aliveCells.add(new Point2D.Double(10, 11));
        aliveCells.add(new Point2D.Double(12, 11));
        aliveCells.add(new Point2D.Double(11, 12));
        aliveCells.add(new Point2D.Double(12, 12));

        LivingBoard game = new LivingBoard(gui, aliveCells, 10, 50, 50, Color.WHITE, Color.BLACK, Color.LIGHT_GRAY);
    }
}

class LivingBoard extends Board implements Simulable{
    private final GUISimulator gui;
    private final int cellSize;
    private final Color aliveCellColor;
    private final Color deadCellColor;
    private final Color bgColor;
    private final HashSet<Point2D.Double> starter;

    public LivingBoard(GUISimulator gui, HashSet<Point2D.Double> starter, int cellSize, int width, int height, Color aliveCellColor, Color deadCellColor, Color bgColor){
        super(width, height, starter);
        this.gui = gui;
        gui.setSimulable(this);

        this.cellSize = cellSize;
        this.starter = starter;
        this.aliveCellColor = aliveCellColor;
        this.deadCellColor = deadCellColor;
        this.bgColor = bgColor;

        draw();
    }

    public void draw(){
        for (int x = 0; x < getWidth(); x++){

            for (int y = 0; y < getHeight(); y++){
                if (getState(y, x)){
                    gui.addGraphicalElement(new Rectangle(
                            20 + cellSize * x,
                            20 + cellSize * y,
                            bgColor, aliveCellColor, cellSize));
                } else {
                    gui.addGraphicalElement(new Rectangle(
                            20 + cellSize * x,
                            20 + cellSize * y,
                            bgColor, deadCellColor, cellSize));
                }
            }
        }
    }

    public void next(){
        nextGen();
        draw();
    }

    public void restart(){
        initBoard(starter);
        draw();
    }
}
