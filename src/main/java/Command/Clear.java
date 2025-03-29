package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Команда для очистки коллекции.
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Clear.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public Clear(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        collectionManager.clear();
        return new ExecutionResponse(true, "Коллекция успешно очищена");
    }

    @Override
    public String getDescription() {
        return "очистить коллекцию";
    }
}
