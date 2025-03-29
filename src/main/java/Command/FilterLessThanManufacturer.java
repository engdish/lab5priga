package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Команда для фильтрации элементов, где имя производителя меньше заданного.
 */
public class FilterLessThanManufacturer extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды FilterLessThanManufacturer.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public FilterLessThanManufacturer(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        if (args.length != 1) {
            return new ExecutionResponse(false, "Команда 'filter_less_than_manufacturer' требует один аргумент: имя производителя");
        }
        String manufacturerName = args[0];
        String result = collectionManager.filterLessThanManufacturer(manufacturerName);
        if (result.isEmpty()) {
            return new ExecutionResponse(true, "Нет элементов с производителем меньше заданного");
        }
        return new ExecutionResponse(true, result);
    }

    @Override
    public String getDescription() {
        return "вывести элементы, значение поля manufacturer которых меньше заданного";
    }
}
