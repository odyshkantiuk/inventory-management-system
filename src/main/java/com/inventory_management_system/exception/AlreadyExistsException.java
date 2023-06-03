package com.inventory_management_system.exception;

import javax.swing.*;

public class AlreadyExistsException {
    public AlreadyExistsException(String object) {
        JOptionPane.showMessageDialog(null,
                object + " with that name already exists",
                "Error message",
                JOptionPane.ERROR_MESSAGE);
    }
}
