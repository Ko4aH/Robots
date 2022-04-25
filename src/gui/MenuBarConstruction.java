package gui;

import log.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MenuBarConstruction {
    private final MainApplicationFrame parent;

    public MenuBarConstruction(MainApplicationFrame parent) {
        this.parent = parent;
    }

    public JMenuBar generate() {
        JMenuBar menuBar = new JMenuBar();

        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");

        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                parent.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                parent.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> Logger.debug("Новая строка"));
            testMenu.add(addLogMessageItem);
        }

        var optionsMenu = new JMenu("Опции");

        {
            var exitButton = new JMenuItem("Выход");
            exitButton.addActionListener(e -> {
                var result = JOptionPane.showConfirmDialog(parent,
                        "Вы действительно хотите закрыть окно?",
                        "Требуется подтверждение",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            });
            optionsMenu.add(exitButton);
        }

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(optionsMenu);
        return menuBar;
    }


    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(parent);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            // just ignore
        }
    }
}
