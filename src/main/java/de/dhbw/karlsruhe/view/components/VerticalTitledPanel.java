package de.dhbw.karlsruhe.view.components;

import javax.swing.*;

public class VerticalTitledPanel extends JPanel {
    public VerticalTitledPanel(String title) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder(title));
    }
}
