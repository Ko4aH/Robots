package gui;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import static robotLogic.FieldConfig.*;
import static robotLogic.MovementConstants.*;
import static robotLogic.MathOperations.*;

public class GameModel extends Observable {

    private static Timer initTimer() {
        return new Timer("events generator", true);
    }

    public static String REPAINT = "repaint";
    public static String ROBOT_MOVED = "robot moved";

    private volatile double m_robotPositionX = RobotPosition.x;
    private volatile double m_robotPositionY = RobotPosition.y;
    private volatile double m_robotDirection = RobotDirection;

    private volatile int m_targetPositionX = TargetPosition.x;
    private volatile int m_targetPositionY = TargetPosition.y;

    public double getRobotPositionX() {
        return m_robotPositionX;
    }

    public double getRobotPositionY() {
        return m_robotPositionY;
    }

    public double getRobotDirection() {
        return m_robotDirection;
    }

    public int getTargetPositionX() {
        return m_targetPositionX;
    }

    public int getTargetPositionY() {
        return m_targetPositionY;
    }

    public void setTargetPosition(int x, int y) {
        m_targetPositionX = x;
        m_targetPositionY = y;
    }

    public GameModel() {
        Timer m_timer = initTimer();
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setChanged();
                notifyObservers(REPAINT);
                clearChanged();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask() {
            @Override
            public void run() {
                simpleMovement();
                setChanged();
                notifyObservers(ROBOT_MOVED);
                clearChanged();
            }
        }, 0, 10);
    }

    public void simpleMovement() {
        var distance = distance(getTargetPositionX(), getTargetPositionY(), getRobotPositionX(), getRobotPositionY());
        if (distance < 0.5) return;
        var angularVelocity = findAngularVelocity();
        moveRobot(findVelocity(angularVelocity), angularVelocity, WindowsStats.gameWindowSize.x, WindowsStats.gameWindowSize.y);
    }

    private double findAngularVelocity() {
        var angleToTarget = angleTo(getRobotPositionX(), getRobotPositionY(), getTargetPositionX(), getTargetPositionY());
        var deltaAngle = angleToTarget - getRobotDirection();
        return applyLimits(deltaAngle / Duration, -maxAngularVelocity, maxAngularVelocity);
    }

    private double findVelocity(double angularVelocity) {
        var angleToTarget = angleTo(getRobotPositionX(), getRobotPositionY(), getTargetPositionX(), getTargetPositionY());
        var estimatedAngle = getRobotDirection() + angularVelocity * Duration;
        var deltaAngle = angleToTarget - estimatedAngle;
        if (Math.abs(deltaAngle) > 1e-5)
            return 0;
        return maxVelocity;
    }

    private void moveRobot(double velocity, double angularVelocity, int borderX, int borderY) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = m_robotPositionX + velocity / angularVelocity * (Math.sin(m_robotDirection + angularVelocity * Duration) - Math.sin(m_robotDirection));
        if (!Double.isFinite(newX)) newX = m_robotPositionX + velocity * Duration * Math.cos(m_robotDirection);
        double newY = m_robotPositionY - velocity / angularVelocity * (Math.cos(m_robotDirection + angularVelocity * Duration) - Math.cos(m_robotDirection));
        if (!Double.isFinite(newY)) newY = m_robotPositionY + velocity * Duration * Math.sin(m_robotDirection);
        double newDirection = asNormalizedRadians(m_robotDirection + angularVelocity * Duration);
        m_robotPositionX = applyLimits(newX, 0, borderX);
        m_robotPositionY = applyLimits(newY, 0, borderY);
        m_robotDirection = newDirection;
    }
}