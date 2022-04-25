package gui;

import stateSaving.SaveableObjJInternalFrame;

import java.awt.*;

import javax.swing.*;

public class GameWindow extends SaveableObjJInternalFrame {
    public GameWindow(GameModel model) {
        super("Игровое поле", true, true, true, true);
        setName("model");
        GameVisualizer visualizer = new GameVisualizer(model);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
