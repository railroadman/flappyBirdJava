package org.example.flappy.observer;

import org.example.flappy.Event;
import org.example.flappy.listeners.Listener;

public interface Notify {
    void addListener(Listener observer);

    void removeListener(Listener observer);

    void notifyListeners(Event event);
}
