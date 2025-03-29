package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;
import Class.Product;

/**
 * Команда для добавления элемента, если его значение меньше минимального.
 */
public class AddIfMin extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды AddIfMin.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public AddIfMin(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        try {
            Product product = console.readProduct(-1);
            if (collectionManager.addIfMin(product)) {
                return new ExecutionResponse(true, "Продукт добавлен, так как он меньше минимального");
            } else {
                return new ExecutionResponse(false, "Продукт не добавлен, так как он не меньше минимального");
            }
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка при добавлении: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента";
    }
}
