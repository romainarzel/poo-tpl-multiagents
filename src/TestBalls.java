import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.*;

public class TestBalls {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);

        Balls balls = new Balls(gui, Color.GREEN, 2);
    }
}


class Balls implements Simulable{
    private GUISimulator gui;

    private Color ballsColor;

    private int ballsRadius;

    private Iterator<Integer> dxIterator;

    private Iterator<Integer> dyIterator;

    private List<Point> points;

    public Balls(GUISimulator gui, Color ballsColor, int ballsRadius){
        this.gui = gui;
        gui.setSimulable(this);
        this.ballsColor = ballsColor;
        this.ballsRadius = ballsRadius;

        planCoordinates();
        draw();
    }

    private void planCoordinates(){

        List<Point> newPoints = new ArrayList<Point>();

        for (int x = 10; x < gui.getPanelHeight(); x += 50){
            for (int y = 10; y < gui.getPanelWidth(); y += 50){
                newPoints.add(new Point(x, y));
            }
        }

        this.points = new ArrayList<Point>(newPoints);

        ArrayList<Integer> xCoos = new ArrayList<Integer>();
        ArrayList<Integer> yCoos = new ArrayList<Integer>();

        for (int loop = 0; loop < 20; loop++){
            xCoos.add(10);
            yCoos.add(0);
            xCoos.add(10);
            yCoos.add(0);

            xCoos.add(0);
            yCoos.add(10);
            xCoos.add(0);
            yCoos.add(10);

            xCoos.add(-10);
            yCoos.add(0);
            xCoos.add(-10);
            yCoos.add(0);

            xCoos.add(0);
            yCoos.add(-10);
            xCoos.add(0);
            yCoos.add(-10);
        }

        dxIterator = xCoos.iterator();
        dyIterator = yCoos.iterator();
    }

    private void draw(){
        gui.reset();

        for (Point point : points){
            gui.addGraphicalElement(new Oval(point.x, point.y, ballsColor, ballsColor, ballsRadius));
        }

    }

    public void next(){
        int dx = 0;
        int dy = 0;

        if (dxIterator.hasNext()){
            dx = dxIterator.next();
        } if (dyIterator.hasNext()){
            dy = dyIterator.next();
        }
        for (Point point : points){
            point.translate(dx, dy);
        }

        draw();
    }

    public void restart(){
        planCoordinates();
        draw();
    }

}
