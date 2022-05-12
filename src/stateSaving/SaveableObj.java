package stateSaving;

import java.util.Map;

/**
 * Интерфейс для сохранения и восстановления состояния окон приложения
 */
public interface SaveableObj {
    /**
     * Сохраняет состояние окна приложения
     */
    void saveState();

    /**
     * Восстанавливает состояние окна приложения
     *
     * @param data сохраненные данные о состоянии окон
     */
    void loadState(Map<String, WindowState> data);
}