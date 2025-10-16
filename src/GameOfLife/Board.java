package GameOfLife;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;

public class Board {
    private ArrayList<ArrayList<Cell>> cellBoard;
    private ArrayList<ArrayList<Integer>> changeBoard;
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
        initNbAliveNeighbours();
        initChangeBoard();
    }

    public void initBoard(){
        setCellBoard();
        initNbAliveNeighbours();
        initChangeBoard();
    }

    public boolean getState(int lig, int col){
        return cellBoard.get(lig).get(col).getState();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public void setCellBoard(){
        cellBoard = new ArrayList<>();

        for (int lig = 0; lig < height; lig++){
            cellBoard.add(new ArrayList<>());

            for (int col = 0; col < width; col++){
                cellBoard.get(lig).add(new Cell());
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
                    cellBoard.get(lig).add(new Cell(true));
                } else {
                    cellBoard.get(lig).add(new Cell(false));
                }
            }
        }
    }

    private void initChangeBoard(){
        changeBoard = new ArrayList<>();

        for (int lig = 0; lig < height; lig++){
            changeBoard.add(new ArrayList<>());

            for (int col = 0; col < width; col++){
                changeBoard.get(lig).add(0);
            }
        }
    }

    public void initNbAliveNeighbours(){
        for (int lig = 0; lig < width; lig++){
            for (int col = 0; col < height; col++){
                initNbAliveNeighboursLocal(lig, col);
            }
        }
    }

    private void initNbAliveNeighboursLocal(int lig, int col){
        Point2D.Double centerCell = new Point2D.Double(lig, col);
        Point2D.Double adjCell = new Point2D.Double();
        int count = 0;

        for (int addLig = -1; addLig <= 1; addLig++){

            for (int addCol = -1; addCol <= 1; addCol++){
                adjCell.setLocation(centerCell.getX() + addCol, centerCell.getY() + addLig);
                adjCell = inBounds(adjCell);

                if (!adjCell.equals(centerCell) & cellBoard.get((int) adjCell.getY()).get((int) adjCell.getX()).getState()){
                    count++;
                }
            }
        }

        cellBoard.get(lig).get(col).changeNeighbours(count);
    }

    public void addToChangeBoard(int lig, int col, int change){
        Point2D.Double adjCell = new Point2D.Double();

        for (int addLig = -1; addLig <= 1; addLig++){

            for (int addCol = -1; addCol <= 1; addCol++){
                adjCell.setLocation(col + addCol, lig + addLig);
                adjCell = inBounds(adjCell);

                if (!(addLig == 0 & addCol == 0)){
                    changeBoard.get((int)adjCell.getY()).set((int)adjCell.getX(), changeBoard.get((int)adjCell.getY()).get((int)adjCell.getX()) + change);
                }
            }
        }


    }

    private Point2D.Double inBounds(Point2D.Double p){
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

        return new Point2D.Double(x, y);
    }

    public void nextGen(){
        initChangeBoard();

        for (int lig = 0; lig < width; lig++){

            for (int col = 0; col < height; col++){
                int change = cellBoard.get(col).get(lig).newGen();
                switch (change){
                    case -1:
                        addToChangeBoard(lig, col, -1);
                        break;

                    case 1:
                        addToChangeBoard(lig, col, 1);
                        break;

                    default :
                        break;
                }
            }

        }
        applyChangeBoard();
    }

    private void applyChangeBoard(){
        for (int lig = 0; lig < width; lig++){
            for (int col = 0; col < height; col++){
                cellBoard.get(lig).get(col).changeNeighbours(changeBoard.get(lig).get(col));
            }
        }
    }
}
