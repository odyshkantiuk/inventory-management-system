package com.inventory_management_system.exception;

import javax.swing.*;

public class LoginException {
    public LoginException() {
        JOptionPane.showMessageDialog(null,
                "Incorrect login or password",
                "Error message",
                JOptionPane.ERROR_MESSAGE);
    }
}
