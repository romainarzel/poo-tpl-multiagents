package cellularSim;

import gui.GUISimulator;

import java.awt.Color;
import java.util.function.IntFunction;

/** Draws a board without coupling simulation rules to GUI traversal details. */
final class BoardRenderer {
    private static final int MARGIN = 20;

    private BoardRenderer() {
    }

    static void draw(GUISimulator gui, Board board, int cellSize, Color background,
            IntFunction<Color> colorForPercent) {
        gui.reset();
        for (int col = 0; col < board.getWidth(); col++) {
            for (int lig = 0; lig < board.getHeight(); lig++) {
                Color cellColor = colorForPercent.apply(board.getPercent(lig, col));
                gui.addGraphicalElement(new gui.Rectangle(
                        MARGIN + cellSize * col,
                        MARGIN + cellSize * lig,
                        background,
                        cellColor,
                        cellSize));
            }
        }
    }
}
