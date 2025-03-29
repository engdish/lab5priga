package Managment;

import Class.Product;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

/**
 * Класс для работы с файлом, содержащим коллекцию продуктов в формате JSON.
 */
public class FileManagerJson {
    private final String fileName;

    /**
     * Конструктор FileManagerJson.
     * @param fileName имя файла
     */
    public FileManagerJson(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("Имя файла не может быть пустым");
        }
        this.fileName = fileName;
    }

    /**
     * Читает коллекцию продуктов из файла.
     * @return коллекция продуктов
     */
    public Vector<Product> readCollection() {
        Vector<Product> products = new Vector<>();
        File file = new File(fileName);
        if (!file.exists()) return products;
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder json = new StringBuilder();
            while (scanner.hasNextLine()) {
                json.append(scanner.nextLine());
            }
            String jsonStr = json.toString().trim();
            if (jsonStr.isEmpty()) return products;
            // Простейший разбор JSON-массива: файл должен иметь вид [ { ... }, { ... } ]
            if (jsonStr.startsWith("[") && jsonStr.endsWith("]")) {
                jsonStr = jsonStr.substring(1, jsonStr.length() - 1).trim();
                // Разбиваем по разделителю между объектами – данный метод корректно работает при условии верного формата
                String[] productJsons = jsonStr.split("},\\s*\\{");
                for (int i = 0; i < productJsons.length; i++) {
                    String productJson = productJsons[i];
                    if (!productJson.startsWith("{")) {
                        productJson = "{" + productJson;
                    }
                    if (!productJson.endsWith("}")) {
                        productJson = productJson + "}";
                    }
                    try {
                        Product product = Product.fromJson(productJson);
                        products.add(product);
                    } catch (Exception e) {
                        System.err.println("Ошибка парсинга продукта: " + e.getMessage());
                    }
                }
            } else {
                System.err.println("Некорректный формат JSON: ожидается массив объектов");
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return products;
    }

    /**
     * Записывает коллекцию продуктов в файл в формате JSON.
     * @param collection коллекция продуктов
     */
    public void writeCollection(Vector<Product> collection) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            writer.println("[");
            for (int i = 0; i < collection.size(); i++) {
                writer.print("  " + collection.get(i).toJson());
                if (i < collection.size() - 1) writer.println(",");
                else writer.println();
            }
            writer.println("]");
        } catch (Exception e) {
            System.err.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}
