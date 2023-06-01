package com.inventory_management_system.exception;

import javax.swing.*;

public class PasswordsMatchException {
    public PasswordsMatchException() {
        JOptionPane.showMessageDialog(null,
                "Passwords do not match",
                "Error message",
                JOptionPane.ERROR_MESSAGE);
    }
}
