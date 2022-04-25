package robotLogic;

import java.awt.*;

import static robotLogic.MathOperations.*;
import static robotLogic.MovementConstants.*;
import static robotLogic.FieldConfig.*;

public class Field {
    public volatile int borderX;
    public volatile int borderY;

    public Target target;
    public Robot robot;

    public Field(Dimension border) {
        borderX = border.width;
        borderY = border.height;
        target = new Target(TargetPosition);
        robot = new Robot(RobotPosition, RobotDirection);
    }

/*    public void onModelUpdateEvent()
    {
        double distance = distance(target.getX(), target.getY(),
                robot.getX(), robot.getY());
        if (distance < 0.5) { return; }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(robot.getX(), robot.getY(), target.getX(), target.getY());
        double angularVelocity = 0;
        if (angleToTarget > robot.getDirection())
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < robot.getDirection())
        {
            angularVelocity = -maxAngularVelocity;
        }

        robot.move(velocity, angularVelocity, borderX, borderY);
    }*/

    public void simpleMovement() {
        var distance = distance(target.getX(), target.getY(), robot.getX(), robot.getY());
        if (distance < 0.5) return;
        var angularVelocity = findAngularVelocity();
        robot.move(findVelocity(angularVelocity), angularVelocity, borderX, borderY);
    }

    private double findAngularVelocity() {
        var angleToTarget = angleTo(robot.getX(), robot.getY(), target.getX(), target.getY());
        var deltaAngle = angleToTarget - robot.getDirection();
        return applyLimits(deltaAngle / Duration, -maxAngularVelocity, maxAngularVelocity);
    }

    private double findVelocity(double angularVelocity) {
        var angleToTarget = angleTo(robot.getX(), robot.getY(), target.getX(), target.getY());
        var estimatedAngle = robot.getDirection() + angularVelocity * Duration;
        var deltaAngle = angleToTarget - estimatedAngle;
        if (Math.abs(deltaAngle) > 1e-5)
            return 0;
        return maxVelocity;
    }
}
