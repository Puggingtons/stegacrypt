package de.dhbw.karlsruhe.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChangeObserver<T> {
    private final List<Consumer<T>> listeners;

    protected T value;

    public ChangeObserver() {
        listeners = new ArrayList<>();
    }

    public void subscribe(Consumer<T> listener) {
        listeners.add(listener);
    }

    public void unsubscribe(Consumer<T> listener) {
        listeners.remove(listener);
    }

    public void set(T value) {
        if (this.value == value) {
            return;
        }

        this.value = value;
        notifyListeners();
    }

    public T get() {
        return value;
    }

    protected void notifyListeners() {
        listeners.forEach(listener -> listener.accept(value));
    }
}
