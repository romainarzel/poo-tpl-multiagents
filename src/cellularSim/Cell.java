package cellularSim;

public class Cell {
    private final int maxState;
    private final int segSeuil;
    private int state;

    public Cell(){
        state = 0;
        maxState = 1;
        segSeuil = 0;
    }

    public Cell(int state){
        this.state = state;
        maxState = 1;
        segSeuil = 0;
    }

    public Cell(int state, int maxState){
        this.maxState = maxState;
        segSeuil = 0;
        setState(state);
    }

    public Cell(int state, int maxState, int segSeuil){
        this.maxState = maxState;
        this.segSeuil = segSeuil;
        setState(state);
    }

    public int getState(){
        return state;
    }

    public int nextState(){
        return (state + 1) % (maxState + 1);
    }

    private void setState(int state){
        this.state = state % (maxState + 1);
    }

    public float getPercent(){
        return (float) state / maxState;
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
        if (isAlive() && nbAliveNeighbours != 2 && nbAliveNeighbours != 3){
            state = 0;
        } else if (!isAlive() && nbAliveNeighbours == 3){
            state = 1;
        }
    }

    public void newGenImmigration(int nbNeighboursNextState){
        if (nbNeighboursNextState > 2){
            setState(state + 1);
        }
    }

    public boolean newGenSeg(int nbNeighboursDiff){
        return state > 0 && nbNeighboursDiff > segSeuil;
    }

    public int segKill(){
        int state = this.state;
        setState(0);

        return state;
    }

    public void segMove(int state){
        setState(state);
    }

    @Override
    public String toString(){
        return Integer.toString(state);
    }
}