package com.inventory_management_system.view;

import javax.swing.*;

public class InventoryView {
    private JPanel inventoryPanel;
    private JButton button1;
    private JLabel inventoryPanelLabel;

    public InventoryView() {
        button1.addActionListener(e -> inventoryPanelLabel.setText("ok"));
    }

    public JPanel getInventoryPanel() {
        return inventoryPanel;
    }
}
