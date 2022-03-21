package gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import gameLogic.Field;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel
{
    private final Timer timer = initTimer();
    public final Field field;
    public final Dimension robotSize = new Dimension(30, 10);
    private final Dimension robotEyeSize = new Dimension(5, 5);
    private final Dimension targetSize = robotEyeSize;
    private final int robotEyeOffsetX = 10;
    private final int redrawTick = 50;
    private final int movementCalculationTick = 10;
    
    private static Timer initTimer() 
    {
        return new Timer("events generator", true);
    }

    public GameVisualizer(Dimension fieldSize)
    {
        field = new Field(fieldSize);
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, redrawTick);
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                field.simpleMovement();
            }
        }, 0, movementCalculationTick);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                field.target.setPosition(e.getPoint());
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
        drawRobot(g2d, round(field.robot.getX()), round(field.robot.getX()), field.robot.getDirection());
        drawTarget(g2d, field.target.getX(), field.target.getY());
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
        int robotCenterX = round(field.robot.getX());
        int robotCenterY = round(field.robot.getY());
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY); 
        g.setTransform(t);
        g.setColor(Color.ORANGE);
        fillOval(g, robotCenterX, robotCenterY, robotSize.width, robotSize.height);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, robotSize.width, robotSize.height);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + robotEyeOffsetX, robotCenterY, robotEyeSize.width, robotEyeSize.height);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + robotEyeOffsetX, robotCenterY, robotEyeSize.width, robotEyeSize.height);
    }
    
    private void drawTarget(Graphics2D g, int x, int y)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0); 
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, x, y, targetSize.width, targetSize.height);
        g.setColor(Color.BLACK);
        drawOval(g, x, y, targetSize.width, targetSize.height);
    }
}
