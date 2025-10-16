package GameOfLife;

public class Cell {
    private boolean state;
    public int nbAliveNeighbours;

    public Cell(){
        state = false;
        nbAliveNeighbours = 0;
    }

    public Cell(boolean state){
        this.state = state;
        nbAliveNeighbours = 0;
    }

    public void changeNeighbours(int signedChange){
        if (/*signedChange + nbAliveNeighbours < 0 |*/ signedChange + nbAliveNeighbours > 8){
            throw new IllegalArgumentException("Cell can't have more than 8 neighbours !");
        }

        nbAliveNeighbours += signedChange;
    }

    public boolean getState(){
        if (state) {
            return true;
        }

        return false;
    }

    public int newGen(){
        if (state & (nbAliveNeighbours < 2 | nbAliveNeighbours > 3)){
            state = false;
            return -1; // Return that the cell died
        } else if (!state & nbAliveNeighbours == 3){
            state = true;
            return 1; // Return that the cell was born
        }

        return 0; // Return that the cell didn't change
    }

    @Override
    public String toString(){
        return state ? "1" : "0";
    }
}