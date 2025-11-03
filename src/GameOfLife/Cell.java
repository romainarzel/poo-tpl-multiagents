package GameOfLife;

public class Cell {
    private boolean state;

    public Cell(){
        state = false;
    }

    public Cell(boolean state){
        this.state = state;
    }

    public boolean isAlive(){
        if (state) { // Weird code to assure security of private component "state"
            return true;
        }

        return false;
    }

    /*
    * Conditions of life and death :
    * 1) If a dead cell has 3 alive neighbours => a new cell is born
    * 2) If an alive cell has strictly less than 2 neighbours => the cell dies of isolation
    * 3) If an alive cell has strictly more than 3 neighbours => the cell dies of overpopulation
    * 4) If neither of these conditions is fulfilled then the cell doesn't change
     */
    public void newGen(int nbAliveNeighbours){
        if (state & nbAliveNeighbours != 2 & nbAliveNeighbours != 3){
            state = false;
        } else if (!state & nbAliveNeighbours == 3){
            state = true;
        }
    }

    @Override
    public String toString(){
        return state ? "1" : "0";
    }
}