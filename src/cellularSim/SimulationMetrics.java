package cellularSim;

/** Snapshot of the state exposed by a cellular simulation. */
public record SimulationMetrics(long generation, int aliveCells, int totalCells) {
}
