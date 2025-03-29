package Managment;

import Command.*;
import Utils.Console;
import Utils.ExecutionResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для управления командами.
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    private final Console console;

    /**
     * Конструктор CommandManager.
     * @param collectionManager менеджер коллекции
     * @param console объект для работы с консолью
     */
    public CommandManager(CollectionManager collectionManager, Console console) {
        this.console = console;
        commands.put("help", new Help(console, this));
        commands.put("info", new Info(console, collectionManager));
        commands.put("show", new Show(console, collectionManager));
        commands.put("add", new Add(console, collectionManager));
        commands.put("update", new Update(console, collectionManager));
        commands.put("remove_by_id", new RemoveById(console, collectionManager));
        commands.put("clear", new Clear(console, collectionManager));
        commands.put("save", new Save(console, collectionManager));
        commands.put("execute_script", new ExecuteScript(console, this));
        commands.put("exit", new Exit(console, collectionManager));
        commands.put("add_if_min", new AddIfMin(console, collectionManager));
        commands.put("reorder", new Reorder(console, collectionManager));
        commands.put("sort", new Sort(console, collectionManager));
        commands.put("filter_less_than_manufacturer", new FilterLessThanManufacturer(console, collectionManager));
        commands.put("filter_greater_than_manufacturer", new FilterGreaterThanManufacturer(console, collectionManager));
        commands.put("print_unique_price", new PrintUniquePrice(console, collectionManager));
    }

    /**
     * Выполняет команду, заданную строкой.
     * @param commandLine строка команды
     * @return результат выполнения команды
     */
    public ExecutionResponse execute(String commandLine) {
        if (commandLine.isEmpty()) {
            return new ExecutionResponse(false, "Введите команду");
        }
        String[] parts = commandLine.trim().split("\\s+", 2);
        String commandName = parts[0];
        String[] args = parts.length > 1 ? parts[1].split("\\s+") : new String[0];
        Command command = commands.get(commandName);
        if (command == null) {
            return new ExecutionResponse(false, "Команда '" + commandName + "' не найдена");
        }
        return command.apply(args);
    }

    /**
     * Возвращает карту доступных команд.
     * @return карта команд
     */
    public Map<String, Command> getCommands() { return commands; }
}
