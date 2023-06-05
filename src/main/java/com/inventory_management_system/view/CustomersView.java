package com.inventory_management_system.view;

import com.inventory_management_system.controller.CustomerController;
import com.inventory_management_system.model.Customer;
import com.inventory_management_system.model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
/**
 * The CustomersView class represents the graphical user interface for managing customers in the inventory management system.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class CustomersView {
    private JPanel customersPanel;
    private JTextField nameTextField1;
    private JTextField emailTextField1;
    private JTextField phoneTextField1;
    private JTextField addressTextField1;
    private JButton addCustomerButton;
    private JTextField nameTextField2;
    private JTextField emailTextField2;
    private JTextField phoneTextField2;
    private JTextField addressTextField2;
    private JButton searchButton;
    private JButton reloadButton;
    private JButton deleteButton;
    private JButton applyButton;
    private JTable table;

    private final CustomerController customerController = new CustomerController();

    public CustomersView() {
        reloadTable(customerController.getAllCustomers());

        reloadButton.addActionListener(e -> reloadTable(customerController.getAllCustomers()));

        addCustomerButton.addActionListener(e -> {
            String name = nameTextField1.getText();
            String email = emailTextField1.getText();
            String phone = phoneTextField1.getText();
            String address = addressTextField1.getText();
            customerController.addCustomer(new Customer(0, name, email, phone, address));
            reloadTable(customerController.getAllCustomers());
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                customerController.deleteCustomer((Integer) table.getValueAt(selectedRow, 0));
                reloadTable(customerController.getAllCustomers());
            }
        });

        applyButton.addActionListener(e -> {
            List<Customer> customers = new ArrayList<>();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                int id = (Integer) tableModel.getValueAt(i, 0);
                String name = (String) tableModel.getValueAt(i, 1);
                String email = (String) tableModel.getValueAt(i, 2);
                String phone = (String) tableModel.getValueAt(i, 3);
                String address = (String) tableModel.getValueAt(i, 4);
                customers.add(new Customer(id, name, email, phone, address));
            }
            customerController.updateCustomers(customers);
            reloadTable(customerController.getAllCustomers());
        });

        searchButton.addActionListener(e -> {
            String name = nameTextField2.getText();
            String email = emailTextField2.getText();
            String phone = phoneTextField2.getText();
            String address = addressTextField2.getText();
            reloadTable(customerController.getFilteredCustomers(name, email, phone, address));
        });
    }

    public JPanel getCustomersPanel() {
        return customersPanel;
    }

    private void reloadTable(List<Customer> customers) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        model.addColumn("id");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("Phone");
        model.addColumn("Address");
        for (Customer customer : customers) {
            Object[] rowData = {customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone(), customer.getAddress()};
            model.addRow(rowData);
        }
        table.setModel(model);
    }
}
