package Class;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс для хранения информации об организации.
 */
public class Organization {
    private static final AtomicLong idGenerator = new AtomicLong(1);
    private final Long id;
    private final String name;
    private final String fullName;
    private final OrganizationType type;

    /**
     * Конструктор Organization.
     * @param name название организации (не может быть null или пустым)
     * @param fullName полное название организации (может быть null)
     * @param type тип организации (может быть null)
     */
    public Organization(String name, String fullName, OrganizationType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название организации не может быть null или пустой строкой");
        }
        this.id = idGenerator.getAndIncrement();
        this.name = name;
        this.fullName = fullName;
        this.type = type;
    }

    /**
     * Обновляет генератор id.
     * @param newValue новое значение
     */
    public static void updateIdGenerator(long newValue) {
        idGenerator.set(newValue);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getFullName() { return fullName; }
    public OrganizationType getType() { return type; }

    @Override
    public String toString() {
        return String.format("Organization{id=%d, name='%s', fullName='%s', type=%s}",
                id, name, Objects.toString(fullName, "null"), Objects.toString(type, "null"));
    }

    /**
     * Возвращает JSON-представление организации.
     * @return строка JSON
     */
    public String toJson() {
        String fullNameStr = fullName == null ? "null" : "\"" + escapeJson(fullName) + "\"";
        String typeStr = type == null ? "null" : "\"" + type.toString() + "\"";
        return String.format("{\"id\":%d,\"name\":\"%s\",\"fullName\":%s,\"type\":%s}",
                id, escapeJson(name), fullNameStr, typeStr);
    }

    private String escapeJson(String input) {
        return input.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    /**
     * Создаёт объект Organization из JSON-строки.
     * @param json строка в формате JSON
     * @return объект Organization
     * @throws Exception в случае ошибки разбора
     */
    public static Organization fromJson(String json) throws Exception {
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
        }
        String name = extractJsonValue(json, "name").replace("\"", "");
        String fullName = extractJsonValue(json, "fullName");
        fullName = fullName.equals("null") ? null : fullName.replace("\"", "");
        String typeStr = extractJsonValue(json, "type");
        OrganizationType type = typeStr.equals("null") ? null : OrganizationType.valueOf(typeStr.replace("\"", ""));
        return new Organization(name, fullName, type);
    }

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
            int end = json.indexOf(",", start);
            if (end == -1) end = json.length();
            return json.substring(start, end).trim();
        }
    }
}
