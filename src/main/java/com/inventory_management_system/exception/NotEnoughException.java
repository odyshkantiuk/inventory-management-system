package com.inventory_management_system.exception;

import javax.swing.*;

public class NotEnoughException {
    public NotEnoughException(){
        JOptionPane.showMessageDialog(null,
                "Not enough product to sell",
                "Error message",
                JOptionPane.ERROR_MESSAGE);
    }
}
