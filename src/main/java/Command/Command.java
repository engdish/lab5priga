package Command;

import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Абстрактный класс для команд.
 */
public abstract class Command {
    protected final Console console;

    /**
     * Конструктор команды.
     * @param console объект для работы с консолью
     */
    public Command(Console console) {
        this.console = console;
    }

    /**
     * Выполняет команду.
     * @param args аргументы команды
     * @return результат выполнения команды
     */
    public abstract ExecutionResponse apply(String[] args);

    /**
     * Возвращает описание команды.
     * @return описание команды
     */
    public abstract String getDescription();
}
