package Managment;

import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Класс для запуска интерактивного режима выполнения команд.
 */
public class Runner {
    private final CommandManager commandManager;
    private final Console console;

    /**
     * Конструктор Runner.
     * @param commandManager менеджер команд
     * @param console объект для работы с консолью
     */
    public Runner(CommandManager commandManager, Console console) {
        this.commandManager = commandManager;
        this.console = console;
    }

    /**
     * Запускает интерактивный режим.
     */
    public void run() {
        console.println("Программа запущена. Введите 'help' для списка команд.");
        while (true) {
            console.print("> ");
            String commandLine = console.readln();
            if (commandLine == null) {  // Обработка на случай получения null
                console.println("Получен null, завершение программы.");
                break;
            }
            if (commandLine.trim().isEmpty()) continue;
            ExecutionResponse response = commandManager.execute(commandLine);
            console.println(response.getMessage());
            if (!response.isSuccess() && response.getMessage().equals("Программа завершена")) {
                break;
            }
        }
    }
}
