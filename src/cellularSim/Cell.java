package cellularSim;

public class Cell {
    private int state;

    public Cell(){
        state = 0;
    }

    public Cell(int state){
        this.state = state;
    }

    public boolean isAlive(){
        return state != 0;
    }

    /*
    * Conditions of life and death :
    * 1) If a dead cell has 3 alive neighbours => a new cell is born
    * 2) If an alive cell has strictly less than 2 neighbours => the cell dies of isolation
    * 3) If an alive cell has strictly more than 3 neighbours => the cell dies of overpopulation
    * 4) If neither of these conditions is fulfilled then the cell doesn't change
     */
    public void newGenConway(int nbAliveNeighbours){
        if (isAlive() & nbAliveNeighbours != 2 & nbAliveNeighbours != 3){
            state = 0;
        } else if (!isAlive() & nbAliveNeighbours == 3){
            state = 1;
        }
    }

    @Override
    public String toString(){
        return Integer.toString(state);
    }
}