package com.inventory_management_system.exception;

import javax.swing.*;

public class NumberInputException {
    public NumberInputException() {
        JOptionPane.showMessageDialog(null,
                "Invalid number format entered",
                "Error message",
                JOptionPane.ERROR_MESSAGE);
    }
}
