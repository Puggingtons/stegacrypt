package de.dhbw.karlsruhe.util;

import java.util.ArrayList;
import java.util.List;

public class ListChangeObserver<T> extends ChangeObserver<List<T>> {
    private final List<T> entries;

    public ListChangeObserver() {
        entries = new ArrayList<>();
    }

    public void add(T value) {
        entries.add(value);

        set(entries);
    }

    public void remove(T value) {
        entries.remove(value);

        set(entries);
    }
}
