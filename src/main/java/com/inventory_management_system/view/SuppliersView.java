package com.inventory_management_system.view;

import com.inventory_management_system.controller.SupplierController;
import com.inventory_management_system.exception.TooLongException;
import com.inventory_management_system.model.Supplier;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
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
    private JButton moreInfoButton;

    private final SupplierController supplierController = new SupplierController();

    public SuppliersView() {
        reloadTable(supplierController.getAllSuppliers());

        addSupplierButton.addActionListener(e -> {
            String name = nameTextField1.getText();
            String description = descriptionTextField.getText();
            String email = emailTextField1.getText();
            String phone = phoneTextField1.getText();
            String address = addressTextField1.getText();
            supplierController.addSupplier(new Supplier(0, name, description, email, phone, address));
            reloadTable(supplierController.getAllSuppliers());
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

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                moreInfoButton.setVisible(column == 2);
            }
        });

        applyButton.addActionListener(e -> {
            List<Supplier> suppliers = new ArrayList<>();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                int id = (Integer) tableModel.getValueAt(i, 0);
                String name = (String) tableModel.getValueAt(i, 1);
                String description = (String) tableModel.getValueAt(i, 2);
                String email = (String) tableModel.getValueAt(i, 3);
                String phone = (String) tableModel.getValueAt(i, 4);
                String address = (String) tableModel.getValueAt(i, 5);
                suppliers.add(new Supplier(id, name, description, email, phone, address));
            }
            supplierController.updateSuppliers(suppliers);
            reloadTable(supplierController.getAllSuppliers());
        });

        moreInfoButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int id = (int) table.getValueAt(selectedRow, 0);
            new InfoView(supplierController.getSupplierById(id).getDescription());
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
            Object[] rowData = {supplier.getId(), supplier.getName(), supplier.getDescription(), supplier.getEmail(), supplier.getPhone(), supplier.getAddress()};
            model.addRow(rowData);
        }
        table.setModel(model);
    }
}
