package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import log.Logger;
import stateSaving.DataSaver;
import stateSaving.SaveableObj;
import stateSaving.SaveableObjJFrame;

import static gui.WindowsStats.*;

public class MainApplicationFrame extends SaveableObjJFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final SaveableObj[] windowsToSave;
    private final static int inset = 50;

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

        setContentPane(desktopPane);
        setName("main");

        var model = new GameModel();

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = createGameWindow(model);
        addWindow(gameWindow);

        InfoWindow infoWindow = createInfoWindow(model);
        addWindow(infoWindow);

        setJMenuBar(new MenuBarConstruction(this).generate());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
        setExtendedState(MAXIMIZED_BOTH);
        windowsToSave = new SaveableObj[]{logWindow, infoWindow, gameWindow, this};
        DataSaver.load(windowsToSave);

        addCloseEventHandler();
    }

    protected InfoWindow createInfoWindow(GameModel model) {
        InfoWindow infoWindow = new InfoWindow(model);
        infoWindow.setLocation(infoWindowLocation);
        infoWindow.setSize(infoWindowSize.x, infoWindowSize.y);
        return infoWindow;
    }

    protected GameWindow createGameWindow(GameModel model) {
        GameWindow gameWindow = new GameWindow(model);
        gameWindow.setLocation(gameWindowLocation);
        gameWindow.setSize(gameWindowSize.x, gameWindowSize.y);
        return gameWindow;
    }

    protected LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(logWindowLocation.x, logWindowLocation.y);
        logWindow.setSize(logWindowSize.x, logWindowSize.y);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private void addCloseEventHandler() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        e.getWindow(),
                        "Вы действительно хотите закрыть окно?",
                        "Требуется подтверждение",
                        JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    DataSaver.save(windowsToSave);
                    e.getWindow().setVisible(false);
                    System.exit(0);
                }
            }
        });
    }
}
