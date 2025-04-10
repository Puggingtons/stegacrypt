package de.dhbw.karlsruhe.util.observers;

import java.util.ArrayList;
import java.util.List;

public class ListChangeObserver<T> extends ChangeObserver<List<T>> {
    public ListChangeObserver() {
        super();
        value = new ArrayList<>();
    }

    public void add(T value) {
        this.value.add(value);

        notifyListeners();
    }

    public void remove(T value) {
        this.value.remove(value);

        notifyListeners();
    }
}
