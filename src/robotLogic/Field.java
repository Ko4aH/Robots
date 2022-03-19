package robotLogic;

import java.awt.*;

import static robotLogic.MathOperations.*;
import static robotLogic.Constants.*;
import static robotLogic.Robot.*;

public class Field {
    public volatile int targetPositionX = 150;
    public volatile int targetPositionY = 100;

    public void onModelUpdateEvent()
    {
        double distance = distance(targetPositionX, targetPositionY,
                robotPositionX, robotPositionY);
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(robotPositionX, robotPositionY, targetPositionX, targetPositionY);
        double angularVelocity = 0;
        if (angleToTarget > robotDirection)
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < robotDirection)
        {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10);
    }

    public void setTargetPosition(Point p)
    {
        targetPositionX = p.x;
        targetPositionY = p.y;
    }
}
