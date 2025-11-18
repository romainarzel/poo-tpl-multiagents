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

    private final EventManager manager = new EventManager();

    /**
     * Constructeur d'une grille du jeu de la vie de Conway
     * @param gui l'interface graphique
     * @param starter l'ensemble des cellules vivantes
     * @param cellSize la longueur d'un côté d'une cellule
     * @param width la largeur de la grille en cellules
     * @param height la hauteur de la grille en cellules
     * @param maxStateCellColor la couleur d'une cellule d'état maximal
     * @param deadCellColor la couleur d'une cellule morte
     * @param bgColor la couleur du fond de la simulation
     */
    public ConwayBoard(GUISimulator gui, HashSet<Point2D.Double> starter, int cellSize, int width, int height, Color maxStateCellColor, Color deadCellColor, Color bgColor){
        super(width, height, starter);
        this.gui = gui;
        gui.setSimulable(this);

        this.cellSize = cellSize;
        this.starter = starter;
        this.maxStateCellColor = maxStateCellColor;
        this.deadCellColor = deadCellColor;
        this.bgColor = bgColor;

        manager.addEvent(new ConwayStep(manager.getCurrentDate() + 1));

        draw();
    }

    /**
     * Dessine la grille sur l'interface graphique
     */
    public void draw(){
        gui.reset();

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

    /**
     * Dessine l'état suivant sur l'interface graphique
     */
    public void next(){
        manager.next();
    }

    /**
     * Dessine l'état initial sur l'interface graphique
     */
    public void restart(){
        manager.restart();
        setCellBoard(starter);
        draw();
    }

    /**
     * Calculate the next step of the simulation and register it to the event manager
     */
    private class ConwayStep extends Event{
        public ConwayStep(long date){
            super(date);
        }

        @Override
        public void execute(){
            nextGenConway();
            draw();

            manager.addEvent(new ConwayStep(getDate() + 1));
        }
    }
}
