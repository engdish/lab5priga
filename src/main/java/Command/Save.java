package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Команда для сохранения коллекции в файл.
 */
public class Save extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Save.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public Save(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        collectionManager.saveCollection();
        return new ExecutionResponse(true, "Коллекция успешно сохранена в файл");
    }

    @Override
    public String getDescription() {
        return "сохранить коллекцию в файл";
    }
}
