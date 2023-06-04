package com.inventory_management_system.exception;

import javax.swing.*;

public class UnableToDeleteException {
    public UnableToDeleteException(String object) {
        JOptionPane.showMessageDialog(null,
                "Unable to delete " + object,
                "Error message",
                JOptionPane.ERROR_MESSAGE);
    }
}
