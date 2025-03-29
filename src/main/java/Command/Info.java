package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Команда для вывода информации о коллекции.
 */
public class Info extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Info.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public Info(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        return new ExecutionResponse(true, collectionManager.getInfo());
    }

    @Override
    public String getDescription() {
        return "вывести информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
