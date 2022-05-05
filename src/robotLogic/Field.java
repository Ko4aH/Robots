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

    public void simpleMovement() {
        var distance = distance(target.getTargetPositionX(), target.getTargetPositionY(), robot.getRobotPositionX(), robot.getRobotPositionY());
        if (distance < 0.5) return;
        var angularVelocity = findAngularVelocity();
        robot.moveRobot(findVelocity(angularVelocity), angularVelocity, borderX, borderY);
    }

    private double findAngularVelocity() {
        var angleToTarget = angleTo(robot.getRobotPositionX(), robot.getRobotPositionY(), target.getTargetPositionX(), target.getTargetPositionY());
        var deltaAngle = angleToTarget - robot.getRobotDirection();
        return applyLimits(deltaAngle / Duration, -maxAngularVelocity, maxAngularVelocity);
    }

    private double findVelocity(double angularVelocity) {
        var angleToTarget = angleTo(robot.getRobotPositionX(), robot.getRobotPositionY(), target.getTargetPositionX(), target.getTargetPositionY());
        var estimatedAngle = robot.getRobotDirection() + angularVelocity * Duration;
        var deltaAngle = angleToTarget - estimatedAngle;
        if (Math.abs(deltaAngle) > 1e-5)
            return 0;
        return maxVelocity;
    }
}
