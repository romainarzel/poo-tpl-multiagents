package cellularSim;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;

/**
 * Implémentation graphique du jeu de la vie de Conway.
 * 
 * \u003cp\u003e
 * Cette classe étend {@link Board} et implémente {@link Simulable} pour fournir
 * une simulation interactive du célèbre automate cellulaire de John Conway.
 * \u003c/p\u003e
 * 
 * \u003cp\u003e
 * Les règles du jeu de la vie sont :
 * \u003cul\u003e
 * \u003cli\u003eUne cellule morte avec exactement 3 voisins vivants
 * naît\u003c/li\u003e
 * \u003cli\u003eUne cellule vivante avec 2 ou 3 voisins vivants
 * survit\u003c/li\u003e
 * \u003cli\u003eUne cellule vivante avec moins de 2 voisins meurt
 * (isolation)\u003c/li\u003e
 * \u003cli\u003eUne cellule vivante avec plus de 3 voisins meurt
 * (surpopulation)\u003c/li\u003e
 * \u003c/ul\u003e
 * \u003c/p\u003e
 * 
 * \u003cp\u003e
 * La classe gère l'affichage graphique avec un dégradé de couleurs entre les
 * cellules
 * mortes (noir) et vivantes (blanc), ainsi que l'évolution temporelle via un
 * gestionnaire
 * d'événements discrets.
 * \u003c/p\u003e
 * 
 * @see Board
 * @see Cell
 * @see EventManager
 */
class ConwayBoard extends Board implements Simulable {

    // Attributs
    private final GUISimulator gui;
    private final HashSet<Point2D.Double> starter;

    // Caractéristiques des cellules
    private static final int cellSize = 10;
    private static final Color maxStateCellColor = Color.WHITE;
    private static final Color deadCellColor = Color.BLACK;
    private static final Color bgColor = Color.LIGHT_GRAY;

    // Event manager
    private final EventManager manager = new EventManager();

    /**
     * Constructeur d'une grille du jeu de la vie de Conway
     * 
     * @param gui     l'interface graphique
     * @param starter l'ensemble des cellules vivantes
     * @param width   la largeur de la grille en cellules
     * @param height  la hauteur de la grille en cellules
     */
    public ConwayBoard(GUISimulator gui, HashSet<Point2D.Double> starter, int width, int height) {
        super(width, height, starter);
        this.gui = gui;
        gui.setSimulable(this);
        this.starter = starter;

        manager.addEvent(new ConwayStep(manager.getCurrentDate() + 1));
        draw();
    }

    /**
     * Dessine la grille sur l'interface graphique
     */
    public void draw() {
        gui.reset();

        Color cellColor;
        for (int x = 0; x < getWidth(); x++) {

            for (int y = 0; y < getHeight(); y++) {
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
    public void next() {
        manager.next();
    }

    /**
     * Dessine l'état initial sur l'interface graphique
     */
    public void restart() {
        manager.restart();
        setCellBoard(starter);
        draw();
    }

    /**
     * Calculate the next step of the simulation and register it to the event
     * manager
     */
    private class ConwayStep extends Event {
        public ConwayStep(long date) {
            super(date);
        }

        @Override
        public void execute() {
            nextGenConway();
            draw();

            manager.addEvent(new ConwayStep(getDate() + 1));
        }
    }
}
