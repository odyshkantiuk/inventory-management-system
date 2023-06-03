package com.inventory_management_system.view;

import com.inventory_management_system.controller.SupplierController;
import com.inventory_management_system.exception.TooLongException;
import com.inventory_management_system.model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SuppliersView {
    private JPanel suppliersPanel;
    private JButton deleteButton;
    private JTextField nameTextField1;
    private JTextField descriptionTextField;
    private JTextField emailTextField1;
    private JTextField phoneTextField1;
    private JTextField addressTextField1;
    private JButton addSupplierButton;
    private JTable table;
    private JTextField nameTextField2;
    private JTextField emailTextField2;
    private JTextField phoneTextField2;
    private JTextField addressTextField2;
    private JButton searchButton;
    private JButton reloadButton;
    private JButton applyButton;

    private final SupplierController supplierController = new SupplierController();

    public SuppliersView() {
        reloadTable(supplierController.getAllSuppliers());

        addSupplierButton.addActionListener(e -> {
            String name = nameTextField1.getText();
            String description = descriptionTextField.getText();
            String email = emailTextField1.getText();
            String phone = phoneTextField1.getText();
            String address = addressTextField1.getText();
            if (name.length() <= 45 && description.length() <= 255 && email.length() <= 255 && phone.length() <= 20 && address.length() <= 255) {
                supplierController.addSupplier(new Supplier(0, name, description, email, phone, address));
                reloadTable(supplierController.getAllSuppliers());
            } else {
                new TooLongException();
            }
        });

        reloadButton.addActionListener(e -> reloadTable(supplierController.getAllSuppliers()));

        searchButton.addActionListener(e -> {
            String name = nameTextField2.getText();
            String email = emailTextField2.getText();
            String phone = phoneTextField2.getText();
            String address = addressTextField2.getText();
            reloadTable(supplierController.getFilteredSuppliers(name, email, phone, address));
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                supplierController.deleteSupplier((Integer) table.getValueAt(selectedRow, 0));
                reloadTable(supplierController.getAllSuppliers());
            }
        });
    }

    public JPanel getSuppliersPanel() {
        return suppliersPanel;
    }

    private void reloadTable(List<Supplier> suppliers) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        model.addColumn("id");
        model.addColumn("Name");
        model.addColumn("Description");
        model.addColumn("Email");
        model.addColumn("Phone");
        model.addColumn("Address");
        for (Supplier supplier : suppliers) {
            Object[] rowData = {supplier.getId(), supplier.getName(), "...", supplier.getEmail(), supplier.getPhone(), supplier.getAddress()};
            model.addRow(rowData);
        }
        table.setModel(model);
    }
}
