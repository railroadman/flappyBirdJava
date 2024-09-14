package org.example.flappy;

import java.util.HashMap;
import java.util.Map;

public enum EventType {
    GAME_START(1),
    GAME_END(2),
    DIE(3),
    FLY(4),
    POINT(5),
    COLLISION(6),
    SWOOSH(7),
    INTRO_START(8),
    INTRO_END(9),
    ;


    EventType(int id) {
    }

    private int id;

    public int getId() {
        return id;
    }

    private static final Map<Integer, EventType> map = new HashMap<>();

    // Static block to populate the map
    static {
        for (EventType eventType : EventType.values()) {
            map.put(eventType.getId(), eventType);
        }
    }

    // Method to get EventType by id
    public static EventType getById(int id) {
        return map.get(id);
    }
}
