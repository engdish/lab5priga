package Class;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс для хранения информации о продукте.
 */
public class Product implements Comparable<Product> {
    private static final AtomicInteger idGenerator = new AtomicInteger(1);
    private final int id;
    private final String name;
    private final Coordinates coordinates;
    private final LocalDateTime creationDate;
    private final double price;
    private final UnitOfMeasure unitOfMeasure;
    private final Organization manufacturer;

    /**
     * Конструктор Product.
     * @param name название продукта (не может быть null или пустым)
     * @param coordinates координаты (не могут быть null)
     * @param price цена (должна быть больше 0)
     * @param unitOfMeasure единица измерения (может быть null)
     * @param manufacturer производитель (не может быть null)
     */
    public Product(String name, Coordinates coordinates, double price,
                   UnitOfMeasure unitOfMeasure, Organization manufacturer) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название продукта не может быть null или пустой строкой");
        }
        if (coordinates == null) {
            throw new IllegalArgumentException("Координаты продукта не могут быть null");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Цена продукта должна быть больше 0");
        }
        if (manufacturer == null) {
            throw new IllegalArgumentException("Производитель продукта не может быть null");
        }
        this.id = idGenerator.getAndIncrement();
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.manufacturer = manufacturer;
        this.creationDate = LocalDateTime.now();
    }

    /**
     * Обновляет генератор id.
     * @param newValue новое значение
     */
    public static void updateIdGenerator(int newValue) {
        idGenerator.set(newValue);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Coordinates getCoordinates() { return coordinates; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public double getPrice() { return price; }
    public UnitOfMeasure getUnitOfMeasure() { return unitOfMeasure; }
    public Organization getManufacturer() { return manufacturer; }

    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public String toString() {
        return String.format("Product{id=%d, name='%s', coordinates=%s, creationDate=%s, price=%.2f, unitOfMeasure=%s, manufacturer=%s}",
                id, name, coordinates, creationDate, price,
                Objects.toString(unitOfMeasure, "null"), manufacturer);
    }

    /**
     * Возвращает JSON-представление продукта.
     * @return строка JSON
     */
    public String toJson() {
        String unitStr = unitOfMeasure == null ? "null" : "\"" + unitOfMeasure.toString() + "\"";
        return String.format("{\"id\":%d,\"name\":\"%s\",\"coordinates\":%s,\"creationDate\":\"%s\",\"price\":%.2f,\"unitOfMeasure\":%s,\"manufacturer\":%s}",
                id, escapeJson(name), coordinates.toJson(), creationDate.toString(), price, unitStr, manufacturer.toJson());
    }

    private String escapeJson(String input) {
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /**
     * Создаёт объект Product из JSON-строки.
     * @param json строка в формате JSON
     * @return объект Product
     * @throws Exception в случае ошибки разбора
     */
    public static Product fromJson(String json) throws Exception {
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
        } else {
            throw new Exception("Неверный формат JSON для Product");
        }
        String name = extractJsonValue(json, "name").replace("\"", "");
        String coordinatesJson = extractJsonObject(json, "coordinates");
        Coordinates coordinates = Coordinates.fromJson(coordinatesJson);
        String priceStr = extractJsonValue(json, "price");
        double price = Double.parseDouble(priceStr);
        String unitStr = extractJsonValue(json, "unitOfMeasure");
        UnitOfMeasure unit = unitStr.equals("null") ? null : UnitOfMeasure.valueOf(unitStr.replace("\"", ""));
        String manufacturerJson = extractJsonObject(json, "manufacturer");
        Organization manufacturer = Organization.fromJson(manufacturerJson);
        return new Product(name, coordinates, price, unit, manufacturer);
    }

    /**
     * Извлекает значение для заданного ключа (число, null или строку, заключённую в кавычки).
     */
    private static String extractJsonValue(String json, String key) throws Exception {
        String pattern = "\"" + key + "\":";
        int index = json.indexOf(pattern);
        if (index == -1) {
            throw new Exception("Ключ " + key + " не найден");
        }
        int start = index + pattern.length();
        char firstChar = json.charAt(start);
        if (firstChar == '"') {
            int end = json.indexOf("\"", start + 1);
            return json.substring(start, end + 1);
        } else {
            // Числовое значение или null; заканчивается запятой или концом строки
            int end = json.indexOf(",", start);
            if (end == -1) end = json.length();
            return json.substring(start, end).trim();
        }
    }

    /**
     * Извлекает объект (значение в фигурных скобках) для заданного ключа.
     */
    private static String extractJsonObject(String json, String key) throws Exception {
        String pattern = "\"" + key + "\":";
        int index = json.indexOf(pattern);
        if (index == -1) {
            throw new Exception("Ключ " + key + " не найден");
        }
        int start = json.indexOf("{", index);
        if (start == -1) {
            throw new Exception("Объект для ключа " + key + " не найден");
        }
        int braceCount = 0;
        int i = start;
        for (; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') braceCount++;
            else if (c == '}') braceCount--;
            if (braceCount == 0) break;
        }
        return json.substring(start, i + 1);
    }
}
