import java.awt.Color;
import java.awt.Point;

import gui.*;

public class TestGravityBall {
    public static void main(String[] args) {
        GUISimulator gui = new GUISimulator(600, 600, Color.BLACK);

        //GravityBall bob = new GravityBall(gui, Color.RED, 5, 1, 0);
        GravityBall alice = new GravityBall(gui, Color.GREEN, 5, 0.9, 10);
    }
}

class GravityBall extends Point implements Simulable{
    private GUISimulator gui;
    private Color ballColor;
    private int ballRadius;
    private double ballBounce;
    private int xVelocity;
    private int yVelocity;
    private int gravityStrength;

    public GravityBall(GUISimulator gui, Color ballColor, int ballRadius, double ballBounce, int gravityStrength){
        this.gui = gui;
        gui.setSimulable(this);
        this.ballColor = ballColor;
        this.ballRadius = ballRadius;
        this.ballBounce = ballBounce;
        this.gravityStrength = gravityStrength;

        init();
        draw();
    }

    public void addVelocity(int addX, int addY){
        this.xVelocity += addX;
        this.yVelocity += addY;
    }

    private void init(){
        this.x = (this.gui.getPanelHeight()) / 2;
        this.y = (this.gui.getPanelWidth()) / 2;

        this.xVelocity = 50;
        this.yVelocity = 100;
    }

    public void draw(){
        gui.reset();

        gui.addGraphicalElement(new Oval(this.x, this.y, this.ballColor, this.ballColor, this.ballRadius));
        gui.addGraphicalElement(new Text(100, 100, this.ballColor, this.x + " , " + this.y));
        gui.addGraphicalElement(new Text(100, 200, this.ballColor, this.xVelocity + " , " + this.yVelocity));
    }

    public void next(){

        int nextX = this.x + this.xVelocity / 10;
        if (nextX < 0| nextX > this.gui.getPanelWidth()){
            this.addVelocity((int) (-2 * this.xVelocity * this.ballBounce), 0);
        }

        int nextY = this.y - this.yVelocity / 10;
        if (nextY < 0 | nextY > this.gui.getPanelHeight()){
            this.addVelocity(0, (int) (-2 * this.yVelocity * this.ballBounce));
        } else {
            this.addVelocity(0, -this.gravityStrength);
        }

        if (Math.abs(this.xVelocity) < 5){
            this.xVelocity = 0;
        }
        if (Math.abs(this.yVelocity) < 5){
            this.yVelocity = 0;
        }

        this.translate(this.xVelocity / 10, -this.yVelocity / 10);
        draw();
    }

    public void restart(){
        init();
        draw();
    }
}
