package com.inventory_management_system.exception;

import javax.swing.*;

public class TooLongException {
    public TooLongException() {
        JOptionPane.showMessageDialog(null,
                "The values provided are too long",
                "Error message",
                JOptionPane.ERROR_MESSAGE);
    }
}
