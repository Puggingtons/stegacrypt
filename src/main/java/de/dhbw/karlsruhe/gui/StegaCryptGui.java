package de.dhbw.karlsruhe.gui;

import javax.swing.*;

public class StegaCryptGui extends JFrame {

    public StegaCryptGui() {
        super("StegaCrypt");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Encode", new EncodePanel());
        tabbedPane.addTab("Decode", new JPanel());

        add(tabbedPane);
    }
}
