package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel implements Observer {
    private final GameModel m_model;

    public final Dimension robotSize = new Dimension(30, 10);
    private final Dimension robotEyeSize = new Dimension(5, 5);
    private final Dimension targetSize = robotEyeSize;

    public GameVisualizer(GameModel model) {
        m_model = model;
        m_model.addObserver(this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                m_model.target.setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent() {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        drawRobot(g2d, m_model.robot.getRobotPositionX(), m_model.robot.getRobotPositionY(), m_model.robot.getRobotDirection());
        drawTarget(g2d, m_model.target.getTargetPositionX(), m_model.target.getTargetPositionY());
    }

    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2) {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }

    private void drawRobot(Graphics2D g, double x, double y, double direction) {
        var robotCenterX = (int) Math.round(x);
        var robotCenterY = (int) Math.round(y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.ORANGE);
        fillOval(g, robotCenterX, robotCenterY, robotSize.width, robotSize.height);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, robotSize.width, robotSize.height);
        g.setColor(Color.WHITE);
        int robotEyeOffsetX = 10;
        fillOval(g, robotCenterX + robotEyeOffsetX, robotCenterY, robotEyeSize.width, robotEyeSize.height);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX + robotEyeOffsetX, robotCenterY, robotEyeSize.width, robotEyeSize.height);
    }

    private void drawTarget(Graphics2D g, int x, int y) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, targetSize.width, targetSize.height);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, targetSize.width, targetSize.height);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (AdditionalFuncs.areEqual(m_model, o))
            if (AdditionalFuncs.areEqual(GameModel.REPAINT, arg))
                onRedrawEvent();
    }
}
