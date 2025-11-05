import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.*;

public class TestBalls {
    public static void main(String[] args){
        GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
        BallsSimulator balls = new BallsSimulator(gui, Color.GREEN, 10);
    }
}

class Balls{
    private int ballsRadius;
    private ArrayList<Point> points;

    public Balls(int ballsRadius){
        this.ballsRadius = ballsRadius;
        reInit();
    }

    public int getRadius(){
        return this.ballsRadius;
    }

    public ArrayList<Point> getPoints(){
        return this.points;
    }

    public void reInit(){
        points = new ArrayList<Point>();
        for (int x = 10; x < 80; x += 50){
            for (int y = 10; y < 60; y += 50){
                points.add(new Point(x, y));
            }
        }
    }

    public void translate(int dx, int dy){
        for(Point p : points){
            p.translate(dx, dy);
        }
    }

    @Override
    public String toString() {
        String str = "position des balles : \n";
        for (Point p : points){
            str += p.toString() + "\n";
        }
        return str;
    }
}

class BallsSimulator extends Balls implements Simulable{
    private GUISimulator gui;
    private Color ballsColor;
    private Iterator<Integer> dxIterator;
    private Iterator<Integer> dyIterator;

    public BallsSimulator(GUISimulator gui, Color ballsColor, int ballsRadius){
        super(ballsRadius);
        this.gui = gui;
        gui.setSimulable(this);
        this.ballsColor = ballsColor;
        System.out.println(this.toString()); //Q.2 Affiche simplement l'état des balles sans graphismes
        draw();
    }

    private void draw(){
        gui.reset();
        for (Point p : getPoints()){
            gui.addGraphicalElement(new Oval(p.x, p.y, ballsColor, ballsColor, this.getRadius()));
        }
    }

    public void next(){
        int dx = 1;
        int dy = 1;

        this.translate(dx, dy);
        System.out.println(this.toString()); //Q.2 Affiche simplement l'état des balles sans graphismes
        draw(); //Q.3 Affiche graphiquement
    }

    public void restart(){
        //planCoordinates();
        //draw();

        this.reInit();
        System.out.println(this.toString()); //Q.2 Affiche simplement l'état des balles sans graphismes
        draw(); //Q.3 Affiche graphiquement
    }

}
