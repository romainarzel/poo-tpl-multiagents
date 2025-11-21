package cellularSim;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;

/**
 * Implémentation graphique de la variante de l'immigration du jeu de la vie.
 * 
 * \u003cp\u003e
 * Cette classe étend {@link Board} et implémente {@link Simulable} pour fournir
 * une simulation interactive de la variante de l'immigration, une extension du
 * jeu
 * de la vie qui utilise plusieurs états au lieu de deux (mort/vivant).
 * \u003c/p\u003e
 * 
 * \u003cp\u003e
 * La règle de l'immigration :
 * \u003cul\u003e
 * \u003cli\u003eUne cellule d'état n avec plus de 2 voisins d'état (n+1) modulo
 * maxState
 * passe à l'état (n+1)\u003c/li\u003e
 * \u003cli\u003eCela crée des vagues de couleurs qui se propagent à travers la
 * grille\u003c/li\u003e
 * \u003c/ul\u003e
 * \u003c/p\u003e
 * 
 * \u003cp\u003e
 * L'affichage utilise un dégradé de couleurs entre noir (mort) et blanc (état
 * maximal)
 * pour visualiser les différents états des cellules.
 * \u003c/p\u003e
 * 
 * @see Board
 * @see Cell
 * @see EventManager
 */
class ImmigrationBoard extends Board implements Simulable {

    // Attributs
    private final GUISimulator gui;
    private final HashMap<Point2D.Double, Integer> starter;
    private final int maxState;

    // Caractéristiques des cellules
    private static final int cellSize = 10;
    private static final Color maxStateCellColor = Color.WHITE;
    private static final Color deadCellColor = Color.BLACK;
    private static final Color bgColor = Color.LIGHT_GRAY;

    // Event manager
    private final EventManager manager = new EventManager();

    /**
     * Constructeur d'une grille de la variante de l'immigration
     * 
     * @param gui      l'interface graphique
     * @param starter  l'ensemble des cellules vivantes
     * @param maxState l'état maximal des cellules
     * @param width    la largeur de la grille en cellules
     * @param height   la hauteur de la grille en cellules
     */
    public ImmigrationBoard(GUISimulator gui, HashMap<Point2D.Double, Integer> starter, int maxState, int width,
            int height) {
        super(width, height, starter, maxState);
        this.gui = gui;
        gui.setSimulable(this);
        this.starter = starter;
        this.maxState = maxState;

        manager.addEvent(new Step(manager.getCurrentDate() + 1));

        draw();
    }

    /**
     * Dessine la grille sur l'interface graphique
     */
    public void draw() {
        gui.reset();

        Color cellColor;
        for (int col = 0; col < getWidth(); col++) {

            for (int lig = 0; lig < getHeight(); lig++) {
                cellColor = linearColorGradient(deadCellColor, maxStateCellColor, (float) getPercent(lig, col) / 100);

                gui.addGraphicalElement(new gui.Rectangle(
                        20 + cellSize * col,
                        20 + cellSize * lig,
                        bgColor, cellColor, cellSize));

            }
        }
    }

    /**
     * Dessine l'état suivant sur l'interface graphique
     */
    public void next() {
        manager.next();
    }

    /**
     * Dessine l'état initial sur l'interface graphique
     */
    public void restart() {
        manager.restart();
        setCellBoard(starter, maxState);
        draw();
    }

    /**
     * Calculate the next step of the simulation and register it to the event
     * manager
     */
    private class Step extends Event {
        public Step(long date) {
            super(date);
        }

        @Override
        public void execute() {
            nextGenImmigration();
            draw();

            manager.addEvent(new Step(manager.getCurrentDate() + 1));
        }
    }
}
