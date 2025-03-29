package Managment;

import Class.Product;
import Class.Organization;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Класс для управления коллекцией продуктов.
 */
public class CollectionManager {
    private final Vector<Product> collection;
    private final LocalDateTime initializationDate;
    private final FileManagerJson fileManager;

    /**
     * Конструктор CollectionManager.
     * @param fileManager менеджер файлов
     */
    public CollectionManager(FileManagerJson fileManager) {
        this.fileManager = fileManager;
        this.collection = fileManager.readCollection();
        this.initializationDate = LocalDateTime.now();
        updateIdGenerators();
    }

    private void updateIdGenerators() {
        if (!collection.isEmpty()) {
            int maxProductId = collection.stream().mapToInt(Product::getId).max().orElse(0);
            Product.updateIdGenerator(maxProductId + 1);
            long maxOrgId = collection.stream()
                    .map(p -> p.getManufacturer().getId())
                    .mapToLong(Long::longValue)
                    .max()
                    .orElse(0);
            Organization.updateIdGenerator(maxOrgId + 1);
        }
    }

    /**
     * Сохраняет коллекцию в файл.
     */
    public void saveCollection() {
        fileManager.writeCollection(collection);
    }

    /**
     * Добавляет продукт в коллекцию.
     * @param product продукт
     */
    public void add(Product product) { collection.add(product); }

    /**
     * Обновляет продукт по заданному id.
     * @param id id продукта
     * @param product новый продукт
     * @return true, если продукт обновлён
     */
    public boolean update(int id, Product product) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getId() == id) {
                collection.set(i, product);
                return true;
            }
        }
        return false;
    }

    /**
     * Удаляет продукт по id.
     * @param id id продукта
     * @return true, если продукт удалён
     */
    public boolean removeById(int id) { return collection.removeIf(p -> p.getId() == id); }

    /**
     * Очищает коллекцию.
     */
    public void clear() { collection.clear(); }

    /**
     * Возвращает информацию о коллекции.
     * @return строка с информацией
     */
    public String getInfo() {
        return String.format("Тип: %s, Дата инициализации: %s, Количество элементов: %d",
                collection.getClass().getSimpleName(), initializationDate, collection.size());
    }

    /**
     * Возвращает коллекцию.
     * @return коллекция продуктов
     */
    public Vector<Product> getCollection() { return collection; }

    /**
     * Добавляет продукт, если он меньше минимального.
     * @param product продукт
     * @return true, если продукт добавлен
     */
    public boolean addIfMin(Product product) {
        if (collection.isEmpty() || product.compareTo(Collections.min(collection)) < 0) {
            add(product);
            return true;
        }
        return false;
    }

    /**
     * Разворачивает коллекцию.
     */
    public void reorder() { Collections.reverse(collection); }

    /**
     * Сортирует коллекцию в естественном порядке.
     */
    public void sort() { Collections.sort(collection); }

    /**
     * Фильтрует продукты, у которых имя производителя меньше заданного.
     * @param manufacturerName имя производителя
     * @return отфильтрованные продукты в строковом виде
     */
    public String filterLessThanManufacturer(String manufacturerName) {
        return collection.stream()
                .filter(p -> p.getManufacturer().getName().compareTo(manufacturerName) < 0)
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Фильтрует продукты, у которых имя производителя больше заданного.
     * @param manufacturerName имя производителя
     * @return отфильтрованные продукты в строковом виде
     */
    public String filterGreaterThanManufacturer(String manufacturerName) {
        return collection.stream()
                .filter(p -> p.getManufacturer().getName().compareTo(manufacturerName) > 0)
                .map(Product::toString)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Выводит уникальные значения цены.
     * @return уникальные цены в строковом виде
     */
    public String printUniquePrice() {
        return collection.stream()
                .map(p -> Double.toString(p.getPrice()))
                .distinct()
                .collect(Collectors.joining("\n"));
    }
}
