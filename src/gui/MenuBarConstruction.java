package gui;

import log.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.Locale;

public class MenuBarConstruction {
    private final MainApplicationFrame parent;

    public MenuBarConstruction(MainApplicationFrame parent) {
        this.parent = parent;
    }

    public JMenuBar generate() {
        JMenuBar menuBar = new JMenuBar();
        var locale = LocaleResources.getResources();

        JMenu lookAndFeelMenu = new JMenu(locale.get("MenuBar.LookAndFeelMenu"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                locale.get("MenuBar.LookAndFeelMenu.Description"));

        {
            JMenuItem systemLookAndFeel = new JMenuItem(
                    locale.get("MenuBar.LookAndFeelMenu.SystemLookAndFeel"),
                    KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                parent.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossPlatformLookAndFeel = new JMenuItem(
                    locale.get("MenuBar.LookAndFeelMenu.CrossPlatformLookAndFeel"),
                    KeyEvent.VK_S);
            crossPlatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                parent.invalidate();
            });
            lookAndFeelMenu.add(crossPlatformLookAndFeel);
        }

        JMenu testMenu = new JMenu(locale.get("MenuBar.TestMenu"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem(
                    locale.get("MenuBar.TestMenu.AddLogMessageItem"),
                    KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> Logger.debug(locale.get("MenuBar.TestMenu.NewMessage")));
            testMenu.add(addLogMessageItem);
        }

        var optionsMenu = new JMenu(locale.get("MenuBar.OptionsMenu"));

        {
            var languageSelection = new JMenu(locale.get("MenuBar.Language"));
            {
                var englishLanguage = new JMenuItem(locale.get("MenuBar.English"));
                englishLanguage.addActionListener(e -> {
                    JOptionPane.showMessageDialog(parent,
                            "The selected changes will take effect after restarting the application");
                    LocaleResources.saveLocale(new Locale("en"));
                });
                languageSelection.add(englishLanguage);
            }
            {
                var russianLanguage = new JMenuItem(locale.get("MenuBar.Russian"));
                russianLanguage.addActionListener(e -> {
                    JOptionPane.showMessageDialog(parent,
                            "Выбранные изменения вступят в силу после перезапуска приложения");
                    LocaleResources.saveLocale(new Locale("ru"));
                });
                languageSelection.add(russianLanguage);
            }
            optionsMenu.add(languageSelection);
        }

        {
            var exitButton = new JMenuItem(locale.get("MenuBar.Exit"));
            exitButton.addActionListener(e -> {
                var result = JOptionPane.showConfirmDialog(parent,
                        locale.get("ConfirmDialog.Message"),
                        locale.get("ConfirmDialog.Title"),
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
