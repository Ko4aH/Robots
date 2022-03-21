package gameLogic;

import java.awt.*;

public class Target {
    private volatile int x;
    private volatile int y;

    public Target(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public void setPosition(Point p) {
        x = p.x;
        y = p.y;
    }
}
