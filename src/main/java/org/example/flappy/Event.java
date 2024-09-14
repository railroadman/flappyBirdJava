package org.example.flappy;


public class Event {
    private int id;
    private EventType eventType;

    public Event(EventType eventType) {
        this.id = eventType.getId();
        this.eventType = eventType;
    }

    public Event(int id) {
        this.id = id;
        this.eventType = EventType.getById(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
