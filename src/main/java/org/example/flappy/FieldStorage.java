package org.example.flappy;

import java.util.HashMap;
import java.util.Map;

public class FieldStorage {

    private Map<Integer, Object> fieldMap = new HashMap<>();

    public <T> void setField(int key, T value) {
        fieldMap.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getField(int key, Class<T> type) {
        Object value = fieldMap.get(key);
        if (value != null && type.isInstance(value)) {
            return (T) value;
        } else {
            throw new IllegalArgumentException("No value found for key or type mismatch");
        }
    }
}