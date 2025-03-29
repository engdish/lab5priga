package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Команда для разворота коллекции (сортировка в обратном порядке).
 */
public class Reorder extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Reorder.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public Reorder(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        collectionManager.reorder();
        return new ExecutionResponse(true, "Коллекция успешно развернута");
    }

    @Override
    public String getDescription() {
        return "отсортировать коллекцию в порядке, обратном нынешнему";
    }
}
