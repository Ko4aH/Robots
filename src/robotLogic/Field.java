package robotLogic;

import java.awt.*;

import static robotLogic.MathOperations.*;
import static robotLogic.Constants.*;

public class Field {
    public volatile int m_targetPositionX = 150;
    public volatile int m_targetPositionY = 100;

    public volatile double m_robotDirection = 0;
    public volatile double m_robotPositionX = 100;
    public volatile double m_robotPositionY = 100;

    public void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection  + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPositionX = newX;
        m_robotPositionY = newY;
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;
    }

    public void onModelUpdateEvent()
    {
        double distance = distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        double angularVelocity = 0;
        if (angleToTarget > m_robotDirection)
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < m_robotDirection)
        {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10);
    }

    public void simpleMovement()
    {
        var distance = distance(m_targetPositionX, m_targetPositionY,
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5) return;
        var angularVelocity = findAngularVelocity();
        moveRobot(findVelocity(angularVelocity), angularVelocity, Duration);

    }

    private double findAngularVelocity()
    {
        var angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        var deltaAngle = angleToTarget - m_robotDirection;
        return applyLimits(deltaAngle / Duration, -maxAngularVelocity, maxAngularVelocity);
    }

    private double findVelocity(double angularVelocity)
    {
        var angleToTarget = angleTo(m_robotPositionX, m_robotPositionY, m_targetPositionX, m_targetPositionY);
        var estimatedAngle = m_robotDirection + angularVelocity * Duration;
        var deltaAngle = angleToTarget - estimatedAngle;
        //Если по намеченному курсу будем отдаляться от цели, то ждём поворота к ней
        //if(Math.abs(deltaAngle) - Math.PI / 2 > 1e-5) return 0;
        //Если ещё не повернули на цель - дожидаемся поворота
        if(Math.abs(deltaAngle) > 1e-5) return 0;
        else {
            var y = (m_targetPositionX + m_targetPositionY + m_robotPositionY - m_robotPositionX) / 2;
            var x = y - m_targetPositionY + m_robotPositionX;
            return applyLimits((distance(x, y, m_robotPositionX, m_robotPositionY) / Duration),
                    -maxVelocity, maxVelocity);
        }
    }

    public void setTargetPosition(Point p)
    {
        m_targetPositionX = p.x;
        m_targetPositionY = p.y;
    }
}
