package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;
import Class.Product;

/**
 * Команда для вывода всех элементов коллекции.
 */
public class Show extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Show.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public Show(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        if (collectionManager.getCollection().isEmpty()) {
            return new ExecutionResponse(true, "Коллекция пуста");
        }
        StringBuilder sb = new StringBuilder();
        for (Product p : collectionManager.getCollection()) {
            sb.append(p.toString()).append("\n");
        }
        return new ExecutionResponse(true, sb.toString());
    }

    @Override
    public String getDescription() {
        return "вывести все элементы коллекции в строковом представлении";
    }
}
