package Utils;

/**
 * Класс для представления результата выполнения команды.
 */
public class ExecutionResponse {
    private final boolean success;
    private final String message;

    /**
     * Конструктор ExecutionResponse.
     * @param success статус выполнения
     * @param message сообщение результата
     */
    public ExecutionResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Возвращает статус выполнения.
     * @return true, если успешно
     */
    public boolean isSuccess() { return success; }

    /**
     * Возвращает сообщение результата.
     * @return сообщение
     */
    public String getMessage() { return message; }
}
