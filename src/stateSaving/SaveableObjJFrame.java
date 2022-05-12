package stateSaving;

import javax.swing.*;
import java.util.Map;

public class SaveableObjJFrame extends JFrame implements SaveableObj {

    @Override
    public void saveState() {
        var isIcon = false;
        var isMaximum = false;
        switch (getExtendedState()) {
            case ICONIFIED:
                isIcon = true;
                break;
            case MAXIMIZED_BOTH:
                isMaximum = true;
                break;
        }
        var state = new WindowState(getWidth(), getHeight(), getX(), getY(), isIcon, isMaximum, false);
        DataSaver.store(getName(), state);
    }

    @Override
    public void loadState(Map<String, WindowState> data) {
        WindowState state = data.get(getName());
        if (state.isIcon()) setExtendedState(ICONIFIED);
        else if (state.isMaximum()) setExtendedState(MAXIMIZED_BOTH);
        else {
            setExtendedState(NORMAL);
            setSize(state.getWidth(), state.getHeight());
            setLocation(state.getX(), state.getY());
        }
    }
}
