package Command;

import Managment.CommandManager;
import Utils.Console;
import Utils.ExecutionResponse;
import java.io.File;
import java.util.Scanner;

/**
 * Команда для выполнения скрипта из файла.
 */
public class ExecuteScript extends Command {
    private final CommandManager commandManager;

    /**
     * Конструктор команды ExecuteScript.
     * @param console объект для работы с консолью
     * @param commandManager менеджер команд
     */
    public ExecuteScript(Console console, CommandManager commandManager) {
        super(console);
        this.commandManager = commandManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        if (args.length != 1) {
            return new ExecutionResponse(false, "Команда 'execute_script' требует один аргумент: имя файла");
        }
        File file = new File(args[0]);
        if (!file.exists() || !file.canRead()) {
            return new ExecutionResponse(false, "Файл не существует или недоступен для чтения");
        }
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String commandLine = scanner.nextLine().trim();
                if (!commandLine.isEmpty()) {
                    ExecutionResponse response = commandManager.execute(commandLine);
                    console.println(response.getMessage());
                    if (!response.isSuccess()) break;
                }
            }
            return new ExecutionResponse(true, "Скрипт успешно выполнен");
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка при выполнении скрипта: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "считать и исполнить скрипт из указанного файла";
    }
}
