package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;
import Class.Product;

/**
 * Команда для добавления нового элемента в коллекцию.
 */
public class Add extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Add.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public Add(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        try {
            Product product = console.readProduct(-1);
            collectionManager.add(product);
            return new ExecutionResponse(true, "Продукт успешно добавлен в коллекцию");
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка при добавлении: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "добавить новый элемент в коллекцию";
    }
}
