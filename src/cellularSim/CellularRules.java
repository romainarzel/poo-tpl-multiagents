package cellularSim;

/** Built-in rules supported by the cellular simulation engine. */
public enum CellularRules implements CellularRule {
    CONWAY {
        @Override
        public void apply(Board board) {
            board.advanceConway();
        }
    },
    IMMIGRATION {
        @Override
        public void apply(Board board) {
            board.advanceImmigration();
        }
    },
    SEGREGATION {
        @Override
        public void apply(Board board) {
            board.advanceSegregation();
        }
    }
}
