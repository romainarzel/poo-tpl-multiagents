package cellularSim;

/** A rule that advances a cellular board by one generation. */
public interface CellularRule {
    void apply(Board board);
}
