package gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import log.Logger;
import stateSaving.DataSaver;
import stateSaving.SaveableObj;
import stateSaving.SaveableObjJFrame;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается.
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 */
public class MainApplicationFrame extends SaveableObjJFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final SaveableObj[] windowsToSave;
    private final static Point gameWindowSize = new Point(400, 400);
    private final static Point gameWindowLocation = new Point(0, 0);
    private final static Point logWindowSize = new Point(300, 800);
    private final static Point logWindowLocation = new Point(10, 10);
    private final static int inset = 50;

    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);
        setName("main");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setLocation(gameWindowLocation);
        gameWindow.setSize(gameWindowSize.x, gameWindowSize.y);
        addWindow(gameWindow);

        setJMenuBar(new MenuBarConstruction(this).generate());
        pack();
        setExtendedState(MAXIMIZED_BOTH);
        windowsToSave = new SaveableObj[]{logWindow, gameWindow, this};
        DataSaver.load(windowsToSave);
        addCloseEventHandler();
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

//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }
}
