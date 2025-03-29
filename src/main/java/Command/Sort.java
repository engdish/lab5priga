package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Команда для сортировки коллекции в естественном порядке.
 */
public class Sort extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Sort.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public Sort(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        collectionManager.sort();
        return new ExecutionResponse(true, "Коллекция успешно отсортирована");
    }

    @Override
    public String getDescription() {
        return "отсортировать коллекцию в естественном порядке";
    }
}
