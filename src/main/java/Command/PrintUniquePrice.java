package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Команда для вывода уникальных значений поля price.
 */
public class PrintUniquePrice extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды PrintUniquePrice.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public PrintUniquePrice(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        String result = collectionManager.printUniquePrice();
        if (result.isEmpty()) {
            return new ExecutionResponse(true, "В коллекции нет элементов");
        }
        return new ExecutionResponse(true, result);
    }

    @Override
    public String getDescription() {
        return "вывести уникальные значения поля price всех элементов в коллекции";
    }
}
