package Command;

import Managment.CommandManager;
import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Команда для вывода справки по доступным командам.
 */
public class Help extends Command {
    private final CommandManager commandManager;

    /**
     * Конструктор команды Help.
     * @param console объект для работы с консолью
     * @param commandManager менеджер команд
     */
    public Help(Console console, CommandManager commandManager) {
        super(console);
        this.commandManager = commandManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        StringBuilder sb = new StringBuilder("Доступные команды:\n");
        commandManager.getCommands().forEach((name, cmd) ->
                sb.append(name).append(" - ").append(cmd.getDescription()).append("\n"));
        return new ExecutionResponse(true, sb.toString());
    }

    @Override
    public String getDescription() {
        return "вывести справку по доступным командам";
    }
}
