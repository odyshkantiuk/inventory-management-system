package com.inventory_management_system.exception;

import javax.swing.*;

public class AccountCreationException {
    public AccountCreationException() {
        JOptionPane.showMessageDialog(null,
                "An account with that name already exists",
                "Error message",
                JOptionPane.ERROR_MESSAGE);
    }
}
