package GameOfLife;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;

public class Board {
    private ArrayList<ArrayList<Cell>> cellBoard;
    private final int width;
    private final int height;

    public Board(int width, int height){
        this.width = width;
        this.height = height;

        initBoard();
    }

    public Board(int width, int height, HashSet<Point2D.Double> aliveCells){
        this.width = width;
        this.height = height;

        initBoard(aliveCells);
    }

    public void initBoard(HashSet<Point2D.Double> aliveCells){
        setCellBoard(aliveCells);
    }

    public void initBoard(){
        setCellBoard();
    }

    public boolean getState(int lig, int col){
        return cellBoard.get(lig).get(col).isAlive();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    private void setCellBoard(){
        cellBoard = new ArrayList<>();

        for (int lig = 0; lig < height; lig++){
            cellBoard.add(new ArrayList<>());

            for (int col = 0; col < width; col++){
                cellBoard.get(lig).add(new Cell());
            }
        }
    }

    private ArrayList<ArrayList<Integer>> setIntBoard(){
        ArrayList<ArrayList<Integer>> intBoard = new ArrayList<>();

        for (int i = 0; i < height; i++){
            intBoard.add(new ArrayList<>());
            for (int j = 0; j < width; j++){
                intBoard.get(i).add(0);
            }
        }

        return intBoard;
    }

    private void setCellBoard(HashSet<Point2D.Double> aliveCells){
        cellBoard = new ArrayList<>();
        Point2D coords = new Point2D.Double();

        for (int lig = 0; lig < height; lig++){
            cellBoard.add(new ArrayList<>());

            for (int col = 0; col < width; col++){
                coords.setLocation(lig, col);

                if (aliveCells.contains(coords)){
                    cellBoard.get(lig).add(new Cell(true));
                } else {
                    cellBoard.get(lig).add(new Cell(false));
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

                if ((addLig != 0 | addCol != 0) & isCellAlive){ // Warning : need to check if not counting itself
                    count++;
                }
            }
        }

        return count;
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

    public void nextGen(){
        ArrayList<ArrayList<Integer>> listNeighbours = setIntBoard();
        for (int lig = 0; lig < width; lig++){ // Calculate the number of neighbours for each cell
            for (int col = 0; col < height; col++){
                listNeighbours.get(lig).set(col, getNbAliveNeighbours(lig, col));
            }
        }

        for (int lig = 0; lig < width; lig++){ // Change the state of the cell according to the inner laws of the cell
            for (int col = 0; col < height; col++){
                cellBoard.get(lig).get(col).newGen(listNeighbours.get(lig).get(col));
            }
        }
    }
}
