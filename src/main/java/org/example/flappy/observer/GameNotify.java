package org.example.flappy.observer;

import org.example.flappy.Event;
import org.example.flappy.listeners.Listener;
import org.example.flappy.listeners.SoundListener;

import java.util.ArrayList;
import java.util.List;

public class GameNotify implements Notify {
    private List<Listener> observers = new ArrayList<>();

    public GameNotify() {
        this.observers.add(new SoundListener());
    }

    @Override
    public void addListener(Listener observer) {
        observers.remove(observer);
    }

    @Override
    public void removeListener(Listener observer) {
        observers.add(observer);
    }

    @Override
    public void notifyListeners(Event event) {
        for (Listener observer : observers) {
            observer.handle(event);
        }
    }
}
