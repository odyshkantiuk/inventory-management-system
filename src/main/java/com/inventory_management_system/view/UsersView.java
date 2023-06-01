package com.inventory_management_system.view;

import com.inventory_management_system.controller.UserController;
import com.inventory_management_system.exception.PasswordsMatchException;
import com.inventory_management_system.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UsersView {
    private JPanel usersPanel;
    private JTextField searchTextField;
    private JComboBox roleComboBox2;
    private JButton searchButton;
    private JTable table;
    private JButton reloadButton;
    private JTextField nameTextField1;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton addUserButton;
    private JTextField nameTextField2;
    private JPasswordField passwordField3;
    private JPasswordField passwordField4;
    private JButton editAccountButton;
    private JPanel addUserPanel;
    private JComboBox roleComboBox1;
    private JButton deleteButton;

    private UserController userController = new UserController();
    private User user;

    public UsersView() {
        reloadTable(userController.getAllUsers());

        addUserButton.addActionListener(e -> {
            String name = nameTextField1.getText();;
            String role = roleComboBox1.getSelectedItem().toString();
            if (passwordField1.getText().equals(passwordField2.getText())) {
                String password = passwordField1.getText();
                userController.addUser(new User(0, name, password, role));
            } else {
                new PasswordsMatchException();
            }
            reloadTable(userController.getAllUsers());
        });

        editAccountButton.addActionListener(e -> {
            String name = nameTextField2.getText();
            if (passwordField3.getText().equals(passwordField4.getText())) {
                String password = passwordField3.getText();
                userController.updateUser(new User(user.getId(), name, password, user.getRole()));
            } else {
                new PasswordsMatchException();
            }
            reloadTable(userController.getAllUsers());
        });

        reloadButton.addActionListener(e -> {
            reloadTable(userController.getAllUsers());
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Object id = table.getValueAt(selectedRow, 0);
                Object role = table.getValueAt(selectedRow, 2);
                if (!role.equals("director") && !role.equals("admin")){
                    userController.deleteUser((Integer) id);
                } else if (role.equals("admin") && user.getRole().equals("director")) {
                    userController.deleteUser((Integer) id);
                }
            }
            reloadTable(userController.getAllUsers());
        });

        searchButton.addActionListener(e -> {
            String filterName = searchTextField.getText();
            String filterRole = roleComboBox2.getSelectedItem().toString();
            reloadTable(userController.getFilteredUsers(filterName, filterRole));
        });
    }

    public void setUser(User user) {
        this.user = user;
        nameTextField2.setText(user.getName());
    }

    public JPanel getUsersPanel() {
        return usersPanel;
    }

    private void reloadTable(List<User> users) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                Object value = getValueAt(row, 2);
                return !value.equals("director") && column != 0;
            }
        };
        model.addColumn("id");
        model.addColumn("Name");
        model.addColumn("Role");
        for (User user : users) {
            Object[] rowData = {user.getId(), user.getName(), user.getRole()};
            model.addRow(rowData);
        }
        table.setModel(model);
    }

    public void showAddUserPanel(){
        String role = user.getRole();
        if (role.equals("director") || role.equals("admin")) {
            addUserPanel.setVisible(true);
            if (role.equals("director")) {
                roleComboBox1.addItem("admin");
            }
        }
    }
}
