package gui;

import stateSaving.SaveableObjJInternalFrame;

import java.awt.*;
import java.util.ResourceBundle;

import javax.swing.*;

public class GameWindow extends SaveableObjJInternalFrame {
    public GameWindow(GameModel model) {
        super(LocaleResources.getResources().get("GameWindow.Title"),
                true, true, true, true);
        setName("model");
        GameVisualizer visualizer = new GameVisualizer(model);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }
}
