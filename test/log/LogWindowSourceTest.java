package log;

import gui.LogWindow;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class LogWindowSourceTest {
    @Test
    public void simpleTest() {
        var testLog = new LogWindowSource(1);
        testLog.append(LogLevel.Info, "Hello, World!");
        var listener = new LogWindow(testLog);
        assertEquals(1, testLog.m_listeners.size());
        assertEquals(1, testLog.m_messages.size());
    }

    @Test
    public void queueIsLimited() {
        var testLog = new LogWindowSource(1);
        var testMessage = "Goodbye!";
        testLog.append(LogLevel.Info, "Hello, World!");
        testLog.append(LogLevel.Info, testMessage);
        assertEquals(1, testLog.m_messages.size());
        assertEquals(testMessage, testLog.m_messages.removeFirst().getMessage());
    }

    @Test
    public void listenersCanBeUnassigned() {
        var testLog = new LogWindowSource(1);
        testLog.append(LogLevel.Info, "Hello, World!");
        var firstListener = new LogWindow(testLog);
        var secondListener = new LogWindow(testLog);
        assertEquals(2, testLog.m_listeners.size());
        testLog.unregisterListener(firstListener);
        assertEquals(1, testLog.m_listeners.size());
        assertTrue(testLog.m_listeners.contains(secondListener));
    }
}