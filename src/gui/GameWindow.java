package gui;

import stateSaving.SaveableObjJInternalFrame;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends SaveableObjJInternalFrame {
    private final GameVisualizer visualizer;

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        this.setName("model");
        visualizer = new GameVisualizer(getSize());
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                visualizer.field.borderX = e.getComponent().getSize().width - visualizer.robotSize.height;
                visualizer.field.borderY = e.getComponent().getSize().height - visualizer.robotSize.width;
            }
        });

        // зачем здесь еще один блок на закрытие?

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                var result = JOptionPane.showConfirmDialog(e.getInternalFrame(),
                        "Вы действительно хотите закрыть окно?",
                        "Требуется подтверждение",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    e.getInternalFrame().dispose();
                }
            }
        });
    }
}
