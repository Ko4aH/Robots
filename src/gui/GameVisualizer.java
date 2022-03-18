package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import robotLogic.Field;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel
{
    private final Timer m_timer = initTimer();
    private final Field field = new Field();
    private final Point robotSize = new Point(30, 10);
    private final Point robotEyeSize = new Point(5, 5);
    private final Point targetSize = robotEyeSize;
    private final int robotEyeOffsetX = 10;
    private final int redrawTick = 50;
    private final int movementCalculationTick = 10;
    
    private static Timer initTimer() 
    {
        return new Timer("events generator", true);
    }
    
    public GameVisualizer() 
    {
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, redrawTick);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                field.onModelUpdateEvent();
            }
        }, 0, movementCalculationTick);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                field.setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }
    
    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    
    private static int round(double value)
    {
        return (int)(value + 0.5);
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 
        drawRobot(g2d, round(field.m_robotPositionX), round(field.m_robotPositionY), field.m_robotDirection);
        drawTarget(g2d, field.m_targetPositionX, field.m_targetPositionY);
    }
    
    private static void fillOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.fillOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private static void drawOval(Graphics g, int centerX, int centerY, int diam1, int diam2)
    {
        g.drawOval(centerX - diam1 / 2, centerY - diam2 / 2, diam1, diam2);
    }
    
    private void drawRobot(Graphics2D g, int x, int y, double direction)
    {
        int robotCenterX = round(field.m_robotPositionX);
        int robotCenterY = round(field.m_robotPositionY);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY); 
        g.setTransform(t);
        g.setColor(Color.ORANGE);
        fillOval(g, robotCenterX, robotCenterY, robotSize.x, robotSize.y);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, robotSize.x, robotSize.y);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + robotEyeOffsetX, robotCenterY, robotEyeSize.x, robotEyeSize.y);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + robotEyeOffsetX, robotCenterY, robotEyeSize.x, robotEyeSize.y);
    }
    
    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0); 
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, targetSize.x, targetSize.y);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, targetSize.x, targetSize.y);
    }
}
