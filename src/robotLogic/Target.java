package robotLogic;

import java.awt.*;

public class Target {
    private volatile int x;
    private volatile int y;

    public Target(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public int getTargetPositionX() {
        return x;
    }

    public int getTargetPositionY() {
        return y;
    }

    public void setTargetPosition(Point p) {
        x = p.x;
        y = p.y;
    }
}
