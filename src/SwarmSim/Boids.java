package SwarmSim;

import gui.GraphicalElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class Boids extends Element implements GraphicalElement {

    private double size;
    private Color color;

    // Double-buffered future state
    private double nextDirection;
    private double nextVelocity;

    // Parameters
    private static final double NEIGHBOR_RADIUS = 200;
    private static final double SEPARATION_RADIUS = 50;

    public Boids (int x, int y , double velocity, double direction, Color color) {
        this.x = x;
        this.y = y;


        this.velocity = velocity;
        this.direction = direction;
        this.size = 20;
        this.color = color;
    }

    // Compute next orientation + next position, but DO NOT apply yet
    public void updateBoids(List<Boids> all) {

        List<Boids> neighbors = new ArrayList<>();
        for (Boids b : all) {
            if (b == this) continue;
            if (this.distance(b) < NEIGHBOR_RADIUS) {
                neighbors.add(b);
            }
        }

        if (neighbors.isEmpty()) {
            nextDirection = this.direction;
            nextVelocity = this.velocity;
            return;
        }

        nextDirection = this.direction;
        nextVelocity = this.velocity;

        // ----------------- WEIGHTED ALIGNMENT (direction + velocity) -----------------
        double sumVx = this.velocity * Math.sin(this.direction);   // weight = 1 for self
        double sumVy = this.velocity * -Math.cos(this.direction);
        double totalWeight = 1.0;

        for (Boids b : neighbors) {
            double dist = this.distance(b);
            double weight = 1.0 / (dist + 0.0001);

            double bd = b.getDirection();
            double bv = b.getVelocity();

            // Neighbor velocity vector
            double vx = bv * Math.sin(bd);
            double vy = bv * -Math.cos(bd);

            // Weighted accumulation
            sumVx += vx * weight;
            sumVy += vy * weight;

            totalWeight += weight;
        }

// Average velocity vector
        double avgVx = sumVx / totalWeight;
        double avgVy = sumVy / totalWeight;

// Convert back to polar coordinates
        nextVelocity = Math.sqrt(avgVx*avgVx + avgVy*avgVy);
        nextDirection = Math.atan2(avgVx, -avgVy);


}

    // Apply buffered values (second phase)
    public void applyUpdate() {
        this.direction = nextDirection;
        this.velocity = nextVelocity;
        this.x += velocity * Math.sin(direction);
        this.y -= velocity * Math.cos(direction);
    }

    @Override
    public void paint(Graphics2D g2) {
        Path2D.Double triangle = new Path2D.Double();
        double halfBase = size * 0.5;

        // Triangle pointing “up” by default, tip at origin
        triangle.moveTo(0, -size);
        triangle.lineTo( halfBase, 0);
        triangle.lineTo(-halfBase, 0);
        triangle.closePath();

        Graphics2D g = (Graphics2D) g2.create();
        try {
            g.setColor(this.color);
            AffineTransform at = new AffineTransform();
            at.translate(x, y);          // move to boid position
            at.rotate(direction);        // rotate to boid heading
            g.transform(at);
            g.fill(triangle);           // filled triangle (use draw() if you only want outline)
        } finally {
            g.dispose();
        }
    }
}
