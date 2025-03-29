package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;
import Class.Product;

/**
 * Команда для обновления элемента коллекции по id.
 */
public class Update extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Update.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public Update(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        if (args.length != 1) {
            return new ExecutionResponse(false, "Команда 'update' требует один аргумент: id");
        }
        try {
            int id = Integer.parseInt(args[0]);
            if (id <= 0) {
                return new ExecutionResponse(false, "ID должен быть больше 0");
            }
            Product product = console.readProduct(id);
            boolean updated = collectionManager.update(id, product);
            if (updated) {
                return new ExecutionResponse(true, "Элемент с id " + id + " успешно обновлен");
            } else {
                return new ExecutionResponse(false, "Элемент с id " + id + " не найден");
            }
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "ID должен быть числом");
        } catch (Exception e) {
            return new ExecutionResponse(false, "Ошибка при обновлении: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "обновить значение элемента коллекции, id которого равен заданному";
    }
}
