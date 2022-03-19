package robotLogic;

import static robotLogic.Constants.maxAngularVelocity;
import static robotLogic.Constants.maxVelocity;
import static robotLogic.MathOperations.applyLimits;
import static robotLogic.MathOperations.asNormalizedRadians;

public class Robot {

    public static volatile double robotDirection = 0;
    public static volatile double robotPositionX = 100;
    public static volatile double robotPositionY = 100;

    public static void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = robotPositionX + velocity / angularVelocity *
                (java.lang.Math.sin(robotDirection + angularVelocity * duration) -
                        java.lang.Math.sin(robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = robotPositionX + velocity * duration * java.lang.Math.cos(robotDirection);
        }
        double newY = robotPositionY - velocity / angularVelocity *
                (java.lang.Math.cos(robotDirection + angularVelocity * duration) -
                        java.lang.Math.cos(robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = robotPositionY + velocity * duration * java.lang.Math.sin(robotDirection);
        }
        robotPositionX = newX;
        robotPositionY = newY;
        double newDirection = asNormalizedRadians(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
    }
}
