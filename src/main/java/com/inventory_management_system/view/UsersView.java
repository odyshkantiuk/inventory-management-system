package com.inventory_management_system.view;

import com.inventory_management_system.controller.UserController;
import com.inventory_management_system.exception.PasswordsMatchException;
import com.inventory_management_system.exception.TooLongException;
import com.inventory_management_system.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private JButton applyButton;
    private JPanel changePanel;
    private JComboBox tableComboBox = new JComboBox<>(new String[]{"admin", "employee"});

    private final UserController userController = new UserController();
    private User user;

    public UsersView() {
        addUserButton.addActionListener(e -> {
            String name = nameTextField1.getText();
            String role = roleComboBox1.getSelectedItem().toString();
            if (passwordField1.getText().equals(passwordField2.getText())) {
                String password = passwordField1.getText();
                if (name.length() <= 45 && password.length() <= 128 && role.length() <= 45) {
                    userController.addUser(new User(0, name, password, role));
                    reloadTable(user, userController.getAllUsers());
                } else {
                    new TooLongException();
                }
            } else {
                new PasswordsMatchException();
            }
        });

        editAccountButton.addActionListener(e -> {
            String name = nameTextField2.getText();
            if (passwordField3.getText().equals(passwordField4.getText())) {
                String password = passwordField3.getText();
                if (name.length() <= 45 && password.length() <= 128) {
                    userController.updateUser(new User(user.getId(), name, password, user.getRole()));
                    reloadTable(user, userController.getAllUsers());
                } else {
                    new TooLongException();
                }
            } else {
                new PasswordsMatchException();
            }
        });

        reloadButton.addActionListener(e -> {
            reloadTable(user, userController.getAllUsers());
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Object id = table.getValueAt(selectedRow, 0);
                Object role = table.getValueAt(selectedRow, 2);
                if (!role.equals("admin") && !user.getRole().equals("employee")){
                    userController.deleteUser((Integer) id);
                } else if (role.equals("admin") && user.getRole().equals("director")) {
                    userController.deleteUser((Integer) id);
                }
            }
            reloadTable(user, userController.getAllUsers());
        });

        searchButton.addActionListener(e -> {
            String filterName = searchTextField.getText();
            String filterRole = roleComboBox2.getSelectedItem().toString();
            reloadTable(user, userController.getFilteredUsers(filterName, filterRole));
        });

        applyButton.addActionListener(e -> {
            List<User> users = new ArrayList<>();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                int id = (Integer) tableModel.getValueAt(i, 0);
                String name = (String) tableModel.getValueAt(i, 1);
                String password = userController.getUserById(id).getPassword();
                String role = (String) tableModel.getValueAt(i, 2);
                if (name.length() <= 45 && password.length() <= 128 && role.length() <= 45) {
                    users.add(new User(id, name, password, role));
                } else {
                    new TooLongException();
                }
            }
            userController.updateUsers(users);
            reloadTable(user, userController.getAllUsers());
        });
    }

    public void setUser(User user) {
        this.user = user;
        nameTextField2.setText(user.getName());
    }

    public JPanel getUsersPanel() {
        return usersPanel;
    }

    public void reloadTable(User mainUser, List<User> users) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                Object value = getValueAt(row, 2);
                if (!user.getRole().equals("director")) {
                    if (user.getRole().equals("admin") && value.equals("employee")) {
                        return column != 2 && column != 0;
                    }
                    return column != 2 && column != 1 && column != 0;
                }
                return column != 0;
            }
        };
        model.addColumn("id");
        model.addColumn("Name");
        model.addColumn("Role");
        for (User user : users) {
            if (!user.getRole().equals("director")) {
                Object[] rowData = {user.getId(), user.getName(), user.getRole()};
                model.addRow(rowData);
            }
        }
        table.setModel(model);
        if (user.getRole().equals("director")) {
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(2).setCellRenderer(new ComboBoxCellRenderer(tableComboBox));
            columnModel.getColumn(2).setCellEditor(new DefaultCellEditor(tableComboBox));
            table.setRowHeight(20);
        }
    }

    public void showAddUserPanel(){
        String role = user.getRole();
        if (role.equals("director") || role.equals("admin")) {
            addUserPanel.setVisible(true);
            changePanel.setVisible(true);
            if (role.equals("director")) {
                roleComboBox1.addItem("admin");
            }
        }
    }

    private class ComboBoxCellRenderer extends JComboBox<String> implements TableCellRenderer {
        public ComboBoxCellRenderer(JComboBox comboBox) {
            super(comboBox.getModel());
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setSelectedItem(value);
            return this;
        }
    }
}
