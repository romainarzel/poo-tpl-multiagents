package cellularSim;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Board {
    private ArrayList<ArrayList<Cell>> cellBoard;
    private final int width;
    private final int height;

    public Board(int width, int height, HashMap<Point2D.Double, Integer> aliveCells, int maxState){
        this.width = width;
        this.height = height;

        setCellBoard(aliveCells, maxState);
    }

    public Board(int width, int height, HashMap<Point2D.Double, Integer> aliveCells, int maxState, int segSeuil){
        this.width = width;
        this.height = height;

        setCellBoard(aliveCells, maxState, segSeuil);
    }

    public Board(int width, int height, HashSet<Point2D.Double> aliveCells){
        this.width = width;
        this.height = height;
        setCellBoard(aliveCells);
    }

    public float getPercent(int lig, int col){
        return cellBoard.get(lig).get(col).getPercent();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setCellBoard(HashMap<Point2D.Double, Integer> aliveCells, int maxState){
        cellBoard = new ArrayList<>();
        Point2D coords = new Point2D.Double();

        for (int lig = 0; lig < height; lig++){
            cellBoard.add(new ArrayList<>());

            for (int col = 0; col < width; col++){
                coords.setLocation(col, lig);

                cellBoard.get(lig).add(new Cell(aliveCells.getOrDefault(coords, 0), maxState));
            }
        }
    }

    public void setCellBoard(HashMap<Point2D.Double, Integer> aliveCells, int maxState, int segSeuil){
        cellBoard = new ArrayList<>();
        Point2D coords = new Point2D.Double();

        for (int lig = 0; lig < height; lig++){
            cellBoard.add(new ArrayList<>());

            for (int col = 0; col < width; col++){
                coords.setLocation(col, lig);

                cellBoard.get(lig).add(new Cell(aliveCells.getOrDefault(coords, 0), maxState, segSeuil));
            }
        }
    }

    public void setCellBoard(HashSet<Point2D.Double> aliveCells){
        cellBoard = new ArrayList<>();
        Point2D coords = new Point2D.Double();

        for (int lig = 0; lig < height; lig++){
            cellBoard.add(new ArrayList<>());

            for (int col = 0; col < width; col++){
                coords.setLocation(lig, col);

                if (aliveCells.contains(coords)){
                    cellBoard.get(lig).add(new Cell(1));
                } else {
                    cellBoard.get(lig).add(new Cell(0));
                }
            }
        }
    }

    private int getNbAliveNeighbours(int lig, int col){
        int count = 0;
        boolean isCellAlive;

        Point2D.Double adjCell = new Point2D.Double();

        for (int addLig = -1; addLig <= 1; addLig++){
            for (int addCol = -1; addCol <= 1; addCol++){
                adjCell.setLocation(col + addCol, lig + addLig);
                inBounds(adjCell); // If the coords are out of bounds wrap them around the other side

                isCellAlive = cellBoard.get((int)adjCell.getY()).get((int)adjCell.getX()).isAlive();

                if ((addLig != 0 || addCol != 0) && isCellAlive){ // Warning : need to check if not counting itself
                    count++;
                }
            }
        }

        return count;
    }

    private int getNbNeighboursNextState(int lig, int col){
        int count = 0;
        int nextState = cellBoard.get(lig).get(col).nextState();
        int cellState;

        Point2D.Double adjCell = new Point2D.Double();

        for (int addLig = -1; addLig <= 1; addLig++){
            for (int addCol = -1; addCol <= 1; addCol++){
                adjCell.setLocation(col + addCol, lig + addLig);
                inBounds(adjCell); // If the coords are out of bounds wrap them around the other side

                cellState = cellBoard.get((int)adjCell.getY()).get((int)adjCell.getX()).getState();

                if ((addLig != 0 || addCol != 0) && cellState == nextState){ // need to check to not count itself
                    count++;
                }
            }
        }

        return count;
    }

    private int getNbNeighboursDiff(int lig, int col){
        int count = 0;
        int state = cellBoard.get(lig).get(col).getState();
        int cellState;

        Point2D.Double adjCell = new Point2D.Double();

        for (int addLig = -1; addLig <= 1; addLig++){
            for (int addCol = -1; addCol <= 1; addCol++){
                adjCell.setLocation(col + addCol, lig + addLig);
                inBounds(adjCell); // If the coords are out of bounds wrap them around the other side

                cellState = cellBoard.get((int)adjCell.getY()).get((int)adjCell.getX()).getState();

                if ((addLig != 0 || addCol != 0) && cellState != 0 && cellState != state){ // need to check to not count itself
                    count++;
                }
            }
        }

        return count;
    }

    private ArrayList<Point2D.Double> listDeadNeighbours(int lig, int col){
        int length = 8 - getNbAliveNeighbours(lig, col);
        boolean isDead;
        ArrayList<Point2D.Double> deadNeighbours = new ArrayList<>(length);

        for (int addLig = -1; addLig <= 1; addLig++){
            for (int addCol = -1; addCol <= 1; addCol++){
                Point2D.Double adjCell = new Point2D.Double(col + addCol, lig + addLig);
                inBounds(adjCell); // If the coords are out of bounds wrap them around the other side

                isDead = !cellBoard.get((int)adjCell.getY()).get((int)adjCell.getX()).isAlive();

                if ((addLig != 0 || addCol != 0) && isDead){ // need to check to not count itself
                    deadNeighbours.add(adjCell);
                }
            }
        }

        return deadNeighbours;
    }

    /*
    * If the given coordinates are out of bounds
    * => Wrap them around the other side
    * (if leave to left get to the right if leave the top go to bottom and vice versa)
    *
    * It's like a donut but in 2D !!!
     */
    private void inBounds(Point2D.Double p){
        double x = p.getX();
        double y = p.getY();

        if (x < 0){
            x = width - 1;
        } else if (x >= width){
            x = 0;
        }

        if (y < 0){
            y = height - 1;
        } else if (y >= height){
            y = 0;
        }

        p.setLocation(x, y);
    }

    public void nextGenConway(){
        ArrayList<ArrayList<Integer>> listNeighbours = new ArrayList<>();
        for (int lig = 0; lig < height; lig++){ // Calculate the number of neighbours for each cell
            listNeighbours.add(new ArrayList<>());
            for (int col = 0; col < width; col++){
                listNeighbours.get(lig).add(getNbAliveNeighbours(lig, col));
            }
        }

        for (int lig = 0; lig < height; lig++){ // Change the state of the cell according to the inner laws of the cell
            for (int col = 0; col < width; col++){
                cellBoard.get(lig).get(col).newGenConway(listNeighbours.get(lig).get(col));
            }
        }
    }

    public void nextGenImmigration(){
        ArrayList<ArrayList<Integer>> listNeighbours = new ArrayList<>();
        for (int lig = 0; lig < height; lig++){ // Calculate the number of neighbours for each cell
            listNeighbours.add(new ArrayList<>());
            for (int col = 0; col < width; col++){
                listNeighbours.get(lig).add(getNbNeighboursNextState(lig, col));
            }
        }

        for (int lig = 0; lig < height; lig++){ // Change the state of the cell according to the inner laws of the cell
            for (int col = 0; col < width; col++){
                cellBoard.get(lig).get(col).newGenImmigration(listNeighbours.get(lig).get(col));
            }
        }
    }

    public void nextGenSeg(){
        int nbNeighboursDiff;
        int state;
        Point2D newCoords;
        ArrayList<Point2D.Double> deadNeighbours;

        Random rng = new Random();

        for (int lig = 0; lig < height; lig++){
            for (int col = 0; col < width; col++){
                nbNeighboursDiff = getNbNeighboursDiff(lig, col);

                if (cellBoard.get(lig).get(col).newGenSeg(nbNeighboursDiff)){

                    deadNeighbours = listDeadNeighbours(lig, col);
                    if (!deadNeighbours.isEmpty()){
                        newCoords = deadNeighbours.get(rng.nextInt(deadNeighbours.size()));

                        state = cellBoard.get(lig).get(col).segKill();
                        cellBoard.get((int)newCoords.getY()).get((int)newCoords.getX()).segMove(state);
                    }
                }
            }
        }
    }
}
