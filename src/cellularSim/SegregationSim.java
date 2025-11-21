package cellularSim;

import gui.GUISimulator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Random;

/**
 * Classe principale pour lancer une simulation du modèle de ségrégation de
 * Schelling.
 * 
 * <p>
 * Cette classe contient la méthode {@code main} qui initialise et démarre une
 * simulation graphique du modèle de ségrégation de Thomas Schelling, qui
 * démontre
 * comment des préférences individuelles modérées peuvent conduire à une
 * ségrégation
 * collective importante.
 * </p>
 * 
 * <p>
 * La simulation utilise les paramètres suivants :
 * <ul>
 * <li>maxState = 3 : 3 types de populations différentes</li>
 * <li>segSeuil = 2 : une cellule déménage si elle a plus de 2 voisins
 * différents</li>
 * <li>deadCellPercent = 30% : pourcentage de cellules initialement vides</li>
 * </ul>
 * </p>
 * 
 * @see SegBoard
 * @see Board
 */
public class SegregationSim {
    /**
     * Lance la simulation du modèle de ségrégation.
     * 
     * <p>
     * Initialise une grille 50x50 avec 70% de cellules vivantes réparties
     * aléatoirement
     * en 3 types de populations, crée une fenêtre graphique, et démarre la
     * simulation.
     * </p>
     * 
     * @param args arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);

        int width = 50;
        int height = 50;
        int maxState = 3;
        int segSeuil = 2;

        HashMap<Point2D.Double, Integer> aliveCells = new HashMap<>(width * height);
        Random rng = new Random();

        int deadCellPercent = 30;
        // Generates a grid of random cell values
        for (int col = 0; col < width; col++) {
            for (int lig = 0; lig < height; lig++) {
                if (rng.nextInt(100) > deadCellPercent) {
                    aliveCells.put(new Point2D.Double(col, lig), rng.nextInt(maxState) + 1);
                }
            }
        }

        SegBoard game = new SegBoard(gui, aliveCells, maxState, segSeuil, width, height);
    }
}
