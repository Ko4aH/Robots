package gui;

import robotLogic.MathOperations;
import stateSaving.SaveableObjJInternalFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class InfoWindow extends SaveableObjJInternalFrame implements Observer {
    private final GameModel m_model;
    private final Label m_robotX = new Label();
    private final Label m_robotY = new Label();
    private final Label m_direction = new Label();
    private final Label m_targetX = new Label();
    private final Label m_targetY = new Label();
    private final Label m_angleToTarget = new Label();

    public InfoWindow(GameModel model) {
        super("Координаты робота", true, true, true, true);
        setName("position");

        m_model = model;
        m_model.addObserver(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        addRow(panel, "RobotX", m_robotX);
        addRow(panel, "RobotY", m_robotY);
        addRow(panel, "TargetX", m_targetX);
        addRow(panel, "TargetY", m_targetY);
        addRow(panel, "Direction", m_direction);
        addRow(panel, "AngleToTarget", m_angleToTarget);

        getContentPane().add(panel);
        pack();
    }

    private void addRow(JPanel panel, String name, Label label) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.LINE_AXIS));
        row.add(new Label(String.format("%s: ", name)));
        row.add(label);
        panel.add(row);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (AdditionalFuncs.areEqual(m_model, o)) {
            if (AdditionalFuncs.areEqual(GameModel.REPAINT, arg)) {
                var robotX = m_model.robot.getRobotPositionX();
                var robotY = m_model.robot.getRobotPositionY();
                var targetX = m_model.target.getTargetPositionX();
                var targetY = m_model.target.getTargetPositionY();
                var angleToTarget = MathOperations.angleTo(robotX, robotY, targetX, targetY);

                m_robotX.setText(doubleToString(robotX));
                m_robotY.setText(doubleToString(robotY));
                m_targetX.setText(doubleToString(targetX));
                m_targetY.setText(doubleToString(targetY));
                m_direction.setText(doubleToString(m_model.robot.getRobotDirection()));
                m_angleToTarget.setText(doubleToString(angleToTarget));
            }
        }
    }

    private String doubleToString(double number) {
        return String.format("%.2f", number);
    }
}