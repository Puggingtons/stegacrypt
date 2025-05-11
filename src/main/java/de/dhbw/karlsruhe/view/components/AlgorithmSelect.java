package de.dhbw.karlsruhe.view.components;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class AlgorithmSelect<T> extends JComboBox<T> {
    public AlgorithmSelect() {
        this(null, null);
    }

    public AlgorithmSelect(T[] items) {
        this(items, null);
    }

    public AlgorithmSelect(Consumer<T> onSelectionChange) {
        this(null, onSelectionChange);
    }

    public AlgorithmSelect(T[] items, Consumer<T> onSelectionChange) {
        setMaximumSize(new Dimension(200, 25));

        if (items != null) {
            setItems(items);
        }

        if (onSelectionChange != null) {
            addActionListener(_ -> {
                onSelectionChange.accept((T) getSelectedItem());
            });
        }
    }

    public void setItems(T[] items) {
        setModel(new DefaultComboBoxModel<>(items));
    }
}
