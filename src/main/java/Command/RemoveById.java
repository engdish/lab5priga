package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Команда для удаления элемента по id.
 */
public class RemoveById extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды RemoveById.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public RemoveById(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        if (args.length != 1) {
            return new ExecutionResponse(false, "Команда 'remove_by_id' требует один аргумент: id");
        }
        try {
            int id = Integer.parseInt(args[0]);
            if (id <= 0) {
                return new ExecutionResponse(false, "ID должен быть больше 0");
            }
            boolean removed = collectionManager.removeById(id);
            if (removed) {
                return new ExecutionResponse(true, "Элемент с id " + id + " успешно удален");
            } else {
                return new ExecutionResponse(false, "Элемент с id " + id + " не найден");
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "ID должен быть числом");
        }
    }

    @Override
    public String getDescription() {
        return "удалить элемент из коллекции по его id";
    }
}
