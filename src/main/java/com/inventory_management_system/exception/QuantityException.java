package com.inventory_management_system.exception;

import javax.swing.*;

public class QuantityException {
    public QuantityException() {
        JOptionPane.showMessageDialog(null,
                "Quantity must be greater than zero",
                "Error message",
                JOptionPane.ERROR_MESSAGE);
    }
}
