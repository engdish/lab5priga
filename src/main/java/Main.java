import Utils.Console;
import Managment.*;

public class Main {
    /**
     * Точка входа в приложение.
     * @param args аргументы командной строки (ожидается имя файла)
     */
    public static void main(String[] args) {
        Console console = new Console();
        String fileName;

        if (args.length != 1) {
            console.println("Укажите имя файла как аргумент командной строки или введите его сейчас:");
            console.print("Имя файла: ");
            fileName = console.readln().trim();
            if (fileName.isEmpty()) {
                console.println("Имя файла не может быть пустым. Программа завершена.");
                return;
            }
        } else {
            fileName = args[0];
        }

        FileManagerJson fileManager = new FileManagerJson(fileName);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(collectionManager, console);
        Runner runner = new Runner(commandManager, console);
        runner.run();
    }
}
