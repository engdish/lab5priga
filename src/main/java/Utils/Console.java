package Utils;

import Class.*;
import java.util.Scanner;
import java.util.Arrays;

/**
 * Класс для работы с консолью: вывод, чтение строк и ввод составных объектов.
 */
public class Console {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Выводит сообщение без перевода строки.
     * @param message сообщение
     */
    public void print(String message) {
        System.out.print(message);
    }

    /**
     * Выводит сообщение с переводом строки.
     * @param message сообщение
     */
    public void println(String message) {
        System.out.println(message);
    }

    /**
     * Читает строку с консоли. Если обнаружен конец ввода (Ctrl+D), программа завершается.
     * @return введённая строка
     */
    public String readln() {
        try {
            if (!scanner.hasNextLine()) { // Обработка Ctrl+D (EOF)
                println("Ввод завершён (EOF). Завершение программы.");
                System.exit(0);
            }
            return scanner.nextLine();
        } catch (Exception e) {
            println("Ошибка ввода. Завершение программы.");
            System.exit(1);
            return "";
        }
    }

    /**
     * Считывает объект Product с консоли, запрашивая ввод каждого поля.
     * @param id если отрицательное, id генерируется автоматически
     * @return созданный объект Product
     */
    public Product readProduct(int id) {
        String name = readString("Введите название продукта (не пустое):", false);
        float x = readFloat("Введите координату X:");
        Float y = readFloat("Введите координату Y:", false);
        Coordinates coordinates = new Coordinates(x, y);
        double price = readDouble("Введите цену (больше 0):", true);
        UnitOfMeasure unit = readEnum("Введите единицу измерения (METERS, PCS, LITERS) или оставьте пустым:", UnitOfMeasure.class);
        String orgName = readString("Введите название организации (не пустое):", false);
        String fullName = readString("Введите полное название организации (или оставьте пустым):", true);
        OrganizationType type = readEnum("Введите тип организации (COMMERCIAL, PUBLIC, GOVERNMENT, PRIVATE_LIMITED_COMPANY) или оставьте пустым:", OrganizationType.class);
        Organization manufacturer = new Organization(orgName, fullName.isEmpty() ? null : fullName, type);
        return new Product(name, coordinates, price, unit, manufacturer);
    }

    private String readString(String prompt, boolean allowEmpty) {
        while (true) {
            println(prompt);
            String input = readln().trim();
            if (allowEmpty || !input.isEmpty()) return input;
            println("Строка не может быть пустой. Попробуйте снова.");
        }
    }

    private float readFloat(String prompt) {
        while (true) {
            try {
                println(prompt);
                return Float.parseFloat(readln().trim());
            } catch (NumberFormatException e) {
                println("Некорректный ввод. Введите число.");
            }
        }
    }

    private Float readFloat(String prompt, boolean allowEmpty) {
        while (true) {
            println(prompt);
            String input = readln().trim();
            if (input.isEmpty() && allowEmpty) return null;
            try {
                return Float.parseFloat(input);
            } catch (NumberFormatException e) {
                println("Некорректный ввод. Введите число.");
            }
        }
    }

    private double readDouble(String prompt, boolean positive) {
        while (true) {
            try {
                println(prompt);
                double value = Double.parseDouble(readln().trim());
                if (!positive || value > 0) return value;
                println("Значение должно быть больше 0.");
            } catch (NumberFormatException e) {
                println("Некорректный ввод. Введите число.");
            }
        }
    }

    /**
     * Считывает значение перечисления (enum) с консоли, предоставляя пользователю подсказку.
     * Если ввод пустой, возвращает {@code null}. Метод повторяет запрос до получения корректного значения.
     *
     * @param <T>         тип перечисления
     * @param prompt      сообщение-подсказка для ввода
     * @param enumClass   класс перечисления, из которого выбирается значение
     * @return            объект перечисления или {@code null}, если ввод пустой
     * @throws IllegalArgumentException если введённая строка не соответствует ни одному значению перечисления
     */
    private <T extends Enum<T>> T readEnum(String prompt, Class<T> enumClass) {
        while (true) {
            println(prompt);
            println("Доступные варианты: " + Arrays.toString(enumClass.getEnumConstants()));
            String input = readln().trim().toUpperCase();
            if (input.isEmpty()) return null;
            try {
                return Enum.valueOf(enumClass, input);
            } catch (IllegalArgumentException e) {
                println("Некорректное значение. Попробуйте снова.");
            }
        }
    }
}
