package com.inventory_management_system.view;

import javax.swing.*;

public class InfoView {
    public InfoView(String description) {
        JOptionPane.showMessageDialog(null,
                description,
                "Info message",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
