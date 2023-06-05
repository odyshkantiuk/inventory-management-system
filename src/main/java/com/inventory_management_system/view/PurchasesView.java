package com.inventory_management_system.view;

import com.inventory_management_system.controller.ProductController;
import com.inventory_management_system.controller.PurchaseController;
import com.inventory_management_system.exception.NumberInputException;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Purchase;
import com.inventory_management_system.model.Supplier;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.List;

public class PurchasesView {
    private JPanel purchasesPanel;
    private JTable table;
    private JComboBox productComboBox1;
    private JTextField priceTextField;
    private JTextField quantityTextField;
    private JButton deleteButton;
    private JButton applyButton;
    private JButton reloadButton;
    private JButton searchButton;
    private JComboBox productComboBox2;
    private JFormattedTextField fromDateTextField;
    private JFormattedTextField toDateTextField;
    private JButton addPurchaseButton;
    private JLabel supplierLabel;
    private final JComboBox tableProductComboBox = new JComboBox<>();

    private final ProductController productController = new ProductController();
    private final PurchaseController purchaseController = new PurchaseController();

    public PurchasesView() {
        AutoCompleteDecorator.decorate(productComboBox1);
        AutoCompleteDecorator.decorate(productComboBox2);
        AutoCompleteDecorator.decorate(tableProductComboBox);
        reloadTable(purchaseController.getAllPurchases());

        reloadButton.addActionListener(e -> reloadTable(purchaseController.getAllPurchases()));

        productComboBox1.addActionListener(e -> {
            Product product = productController.getProductByName((String) productComboBox1.getSelectedItem());
            if (product != null) {
                priceTextField.setText(String.valueOf(product.getPurchasePrice()));
                supplierLabel.setText(product.getSupplier().getName());
            }
        });

        tableProductComboBox.addActionListener(e -> {
            Product product = productController.getProductByName((String) tableProductComboBox.getSelectedItem());
            if (product != null) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    table.setValueAt(product.getSupplier().getName(), selectedRow, 2);
                    table.setValueAt(String.valueOf(product.getPurchasePrice()), selectedRow, 4);
                }
            }
        });

        addPurchaseButton.addActionListener(e -> {
            String productName = (String) productComboBox1.getSelectedItem();
            Product product = productController.getProductByName((String) productComboBox1.getSelectedItem());
            Supplier supplier = product.getSupplier();
            try {
                double price = Double.parseDouble(priceTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());
                Timestamp time = new Timestamp(System.currentTimeMillis());
                Purchase purchase = new Purchase(0, price, time, product, quantity);
                purchaseController.addPurchase(purchase);
                reloadTable(purchaseController.getAllPurchases());
            } catch (NumberFormatException ex) {
                new NumberInputException();
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                purchaseController.deletePurchase((Integer) table.getValueAt(selectedRow, 0));
                reloadTable(purchaseController.getAllPurchases());
            }
        });
    }

    public JPanel getPurchasesPanel() {
        return purchasesPanel;
    }

    public void reloadProduct() {
        List<Product> products = productController.getAllProducts();
        reloadComboBox(products, productComboBox1, false);
        reloadComboBox(products, productComboBox2, true);
        reloadComboBox(products, tableProductComboBox, false);
    }

    private <T> void reloadComboBox(List<T> objects, JComboBox<String> comboBox, boolean isFilter) {
        comboBox.removeAllItems();
        if (isFilter) {
            comboBox.addItem("");
        }
        for (Object object : objects) {
            comboBox.addItem(object.toString());
        }
    }

    public void reloadTable(List<Purchase> purchases) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 2 && column != 3 && column != 6;
            }
        };
        model.addColumn("id");
        model.addColumn("Product");
        model.addColumn("Supplier");
        model.addColumn("Time");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Total");
        for (Purchase purchase : purchases) {
            Object[] rowData = {purchase.getId(), purchase.getProduct().getName(), purchase.getProduct().getSupplier().getName(), purchase.getTime(), String.valueOf(purchase.getPrice()), String.valueOf(purchase.getQuantity()), String.valueOf(purchase.calculateTotal())};
            model.addRow(rowData);
        }
        table.setModel(model);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(1).setCellRenderer(new ComboBoxCellRenderer(tableProductComboBox));
        columnModel.getColumn(1).setCellEditor(new DefaultCellEditor(tableProductComboBox));
        table.setRowHeight(20);
    }

    private static class ComboBoxCellRenderer extends JComboBox<String> implements TableCellRenderer {
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
