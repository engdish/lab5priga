package Class;

/**
 * Класс для хранения координат.
 */
public class Coordinates {
    private final float x;
    private final Float y;

    /**
     * Конструктор Coordinates.
     * @param x координата X
     * @param y координата Y (не может быть null)
     */
    public Coordinates(float x, Float y) {
        this.x = x;
        if (y == null) {
            throw new IllegalArgumentException("Координата Y не может быть null");
        }
        this.y = y;
    }

    /**
     * Возвращает координату X.
     * @return x
     */
    public float getX() { return x; }

    /**
     * Возвращает координату Y.
     * @return y
     */
    public Float getY() { return y; }

    @Override
    public String toString() {
        return String.format("Coordinates{x=%.1f, y=%.1f}", x, y);
    }

    /**
     * Возвращает JSON-представление объекта.
     * @return строка JSON
     */
    public String toJson() {
        return String.format("{\"x\":%.1f,\"y\":%.1f}", x, y);
    }

    /**
     * Создаёт объект Coordinates из JSON-строки.
     * @param json строка в формате JSON
     * @return объект Coordinates
     * @throws Exception в случае ошибки разбора
     */
    public static Coordinates fromJson(String json) throws Exception {
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
        }
        String xStr = extractJsonValue(json, "x");
        String yStr = extractJsonValue(json, "y");
        float x = Float.parseFloat(xStr);
        float y = Float.parseFloat(yStr);
        return new Coordinates(x, y);
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
