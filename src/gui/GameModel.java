package gui;

import robotLogic.Robot;
import robotLogic.Target;

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

    public Target target;
    public Robot robot;

    public GameModel() {
        target = new Target(TargetPosition);
        robot = new Robot(RobotPosition, RobotDirection);
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
        var distance =
                distance(target.getTargetPositionX(),
                        target.getTargetPositionY(),
                        robot.getRobotPositionX(),
                        robot.getRobotPositionY());
        if (distance < 0.5) return;
        var angularVelocity = findAngularVelocity();
        robot.moveRobot(
                findVelocity(angularVelocity),
                angularVelocity,
                WindowsStats.gameWindowSize.x,
                WindowsStats.gameWindowSize.y);
    }

    private double findAngularVelocity() {
        var angleToTarget =
                angleTo(robot.getRobotPositionX(),
                        robot.getRobotPositionY(),
                        target.getTargetPositionX(),
                        target.getTargetPositionY());
        var deltaAngle = angleToTarget - robot.getRobotDirection();
        return applyLimits(deltaAngle / Duration, -maxAngularVelocity, maxAngularVelocity);
    }

    private double findVelocity(double angularVelocity) {
        var angleToTarget =
                angleTo(robot.getRobotPositionX(),
                        robot.getRobotPositionY(),
                        target.getTargetPositionX(),
                        target.getTargetPositionY());
        var estimatedAngle = robot.getRobotDirection() + angularVelocity * Duration;
        var deltaAngle = angleToTarget - estimatedAngle;
        if (Math.abs(deltaAngle) > 1e-5)
            return 0;
        return maxVelocity;
    }
}