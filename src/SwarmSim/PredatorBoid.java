package SwarmSim;

import java.awt.*;
import java.util.Collections;
import java.util.List;


public class PredatorBoid extends Boids {

    private List<Boids> targets = Collections.emptyList();

    public PredatorBoid(int x, int y, double velocity, double direction, Color color, int wallX, int wallY) {
        super(x, y, velocity, direction, color, wallX, wallY);
    }

    public void setTargets(List<Boids> targets) {
        this.targets = (targets == null) ? Collections.emptyList() : targets;
    }

    @Override
    public void updateBoids(List<Boids> all) {
        super.updateBoids(all);
        if (targets.isEmpty()) return;

        Boids nearest = null;
        double best = Double.distance(x, y, x, y);
        for (Boids t : targets) {
            double d = this.distance(t);
            if (d < best) { best = d; nearest = t; }
        }
        if (nearest == null) return;

        double dx = nearest.x - this.x;
        double dy = nearest.y - this.y;
        double desired = Math.atan2(dx, -dy);

        double alpha = 0.35; // pursuit aggressiveness
        this.nextDirection = blendAngles(this.nextDirection, desired, alpha);

        this.nextVelocity = Math.min(this.nextVelocity + 0.3, 3.5);
    }

    private static double blendAngles(double a, double b, double t) {
        double x1 = Math.sin(a), y1 = Math.cos(a);
        double x2 = Math.sin(b), y2 = Math.cos(b);
        double x = (1 - t) * x1 + t * x2;
        double y = (1 - t) * y1 + t * y2;
        return Math.atan2(x, y);
    }
}

