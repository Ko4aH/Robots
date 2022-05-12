package robotLogic;

import java.awt.*;

import static robotLogic.MovementConstants.*;
import static robotLogic.MathOperations.*;

public class Robot {
    private volatile double x;
    private volatile double y;
    private volatile double direction;

    public Robot(Point p, double direction) {
        this.x = p.x;
        this.y = p.y;
        this.direction = direction;
    }

    public double getRobotPositionX() {
        return x;
    }

    public double getRobotPositionY() {
        return y;
    }

    public double getRobotDirection() {
        return direction;
    }

    public void moveRobot(double velocity, double angularVelocity, int borderX, int borderY) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = x + velocity / angularVelocity * (Math.sin(direction + angularVelocity * Duration) - Math.sin(direction));
        if (!Double.isFinite(newX)) newX = x + velocity * Duration * Math.cos(direction);
        double newY = y - velocity / angularVelocity * (Math.cos(direction + angularVelocity * Duration) - Math.cos(direction));
        if (!Double.isFinite(newY)) newY = y + velocity * Duration * Math.sin(direction);
        double newDirection = asNormalizedRadians(direction + angularVelocity * Duration);
        x = applyLimits(newX, 0, borderX);
        y = applyLimits(newY, 0, borderY);
        direction = newDirection;
    }
}
