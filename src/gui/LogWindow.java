package gui;

import java.awt.*;

import javax.swing.*;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;
import stateSaving.SaveableObjJInternalFrame;

public class LogWindow extends SaveableObjJInternalFrame implements LogChangeListener {
    private LogWindowSource logSource;
    private TextArea logContent;
    private static final Point logContentSize = new Point(200, 500);

    public LogWindow(LogWindowSource logSource) {
        super(LocaleResources.getResources().get("LogWindow.Title"),
                true, true, true, true);
        this.setName("log");
        this.logSource = logSource;
        this.logSource.registerListener(this);
        logContent = new TextArea("");
        logContent.setEditable(false);
        logContent.setSize(logContentSize.x, logContentSize.y);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        logContent.setText(content.toString());
        logContent.invalidate();
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
