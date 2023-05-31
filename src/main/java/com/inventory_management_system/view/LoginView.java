package com.inventory_management_system.view;

import com.inventory_management_system.controller.UserController;
import com.inventory_management_system.model.User;

import javax.swing.*;

public class LoginView extends JFrame {
    private JPanel loginPanel;
    private JTextField loginNameField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;

    public LoginView() {
        setContentPane(loginPanel);
        setTitle("Login to IMS");
        setSize(350,200);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        loginButton.addActionListener(e -> {
            UserController userController = new UserController();
            String name = loginNameField.getText();
            String password = loginPasswordField.getText();
            name = "default";
            password = "12345678";
            User user = userController.getUser(name, password);
            if (user != null) {
                dispose();
                new MainView();
            }
        });
    }
}
