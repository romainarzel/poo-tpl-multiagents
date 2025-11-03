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
        if (state) {
            return true;
        }

        return false;
    }

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