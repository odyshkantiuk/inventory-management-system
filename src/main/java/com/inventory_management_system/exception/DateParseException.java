package com.inventory_management_system.exception;

import javax.swing.*;

public class DateParseException {
    public DateParseException() {
        JOptionPane.showMessageDialog(null,
                "Enter the date correctly: yyyy-MM-dd",
                "Error message",
                JOptionPane.ERROR_MESSAGE);
    }
}
