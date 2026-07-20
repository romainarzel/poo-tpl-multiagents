package cellularSim;

import java.awt.geom.Point2D;
import java.util.HashSet;

/** Lightweight, GUI-free regression checks for the cellular engine. */
public final class CellularEngineTest {
    private CellularEngineTest() {
    }

    public static void main(String[] args) {
        HashSet<Point2D.Double> aliveCells = new HashSet<>();
        aliveCells.add(new Point2D.Double(2, 1));
        aliveCells.add(new Point2D.Double(2, 2));
        aliveCells.add(new Point2D.Double(2, 3));

        Board board = new Board(5, 5, aliveCells);
        CellularRules.CONWAY.apply(board);

        SimulationMetrics metrics = board.getMetrics();
        assertEquals(1, metrics.generation(), "generation");
        assertEquals(3, metrics.aliveCells(), "alive cells");
        assertEquals(25, metrics.totalCells(), "total cells");
        assertEquals(100, board.getPercent(2, 2), "Conway survival");

        board.setCellBoard(aliveCells);
        assertEquals(0, board.getGeneration(), "generation reset");
        System.out.println("Cellular engine tests passed");
    }

    private static void assertEquals(long expected, long actual, String label) {
        if (expected != actual) {
            throw new AssertionError(label + ": expected " + expected + ", got " + actual);
        }
    }
}
