package stateSaving;

import java.io.Serializable;

public class WindowState implements Serializable {
    private final int width;
    private final int height;
    private final int x;
    private final int y;
    private final boolean isIcon;
    private final boolean isMaximum;
    private final boolean isClosed;

    public WindowState(int width, int height, int x, int y, boolean isIcon, boolean isMaximum, boolean isClosed) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.isIcon = isIcon;
        this.isMaximum = isMaximum;
        this.isClosed = isClosed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isIcon() {
        return isIcon;
    }

    public boolean isMaximum() {
        return isMaximum;
    }

    public boolean isClosed() {
        return isClosed;
    }
}
