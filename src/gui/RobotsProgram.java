package gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.util.Locale;

public class RobotsProgram {
    public static void main(String[] args) {
        LocaleResources.init();
        var locale = LocaleResources.getResources();
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("OptionPane.yesButtonText", locale.get("OptionPane.YesButtonText"));
            UIManager.put("OptionPane.noButtonText", locale.get("OptionPane.NoButtonText"));
            UIManager.put("OptionPane.cancelButtonText", locale.get("OptionPane.CancelButtonText"));
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            MainApplicationFrame frame = new MainApplicationFrame();
            frame.setVisible(true);
        });
    }
}
