package Command;

import Managment.CollectionManager;
import Utils.Console;
import Utils.ExecutionResponse;

/**
 * Команда для завершения программы.
 */
public class Exit extends Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды Exit.
     * @param console объект для работы с консолью
     * @param collectionManager менеджер коллекции
     */
    public Exit(Console console, CollectionManager collectionManager) {
        super(console);
        this.collectionManager = collectionManager;
    }

    @Override
    public ExecutionResponse apply(String[] args) {
        console.println("Сохранить коллекцию перед выходом? (yes/no)");
        String response = console.readln().trim().toLowerCase();
        if (response.equals("yes")) {
            collectionManager.saveCollection();
            console.println("Коллекция сохранена.");
        }
        return new ExecutionResponse(false, "Программа завершена");
    }

    @Override
    public String getDescription() {
        return "завершить программу (без сохранения в файл)";
    }
}
