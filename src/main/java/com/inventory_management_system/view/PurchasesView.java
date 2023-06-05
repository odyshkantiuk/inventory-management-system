package com.inventory_management_system.view;

import com.inventory_management_system.controller.ProductController;
import com.inventory_management_system.controller.PurchaseController;
import com.inventory_management_system.exception.DateParseException;
import com.inventory_management_system.exception.NumberInputException;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Purchase;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
/**
 * The PurchasesView class represents the graphical user interface for managing purchases in the inventory management system.
 *
 * @author Oleksandr Dyshkantiuk
 */
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
            Product product = productController.getProductByName((String) productComboBox1.getSelectedItem());
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

        applyButton.addActionListener(e -> {
            List<Purchase> purchases = new ArrayList<>();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                int id = (Integer) tableModel.getValueAt(i, 0);
                String productStr = (String) tableModel.getValueAt(i, 1);
                Timestamp time = (Timestamp) tableModel.getValueAt(i, 3);
                String price = (String) tableModel.getValueAt(i, 4);
                String quantityStr = (String) tableModel.getValueAt(i, 5);
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    Product product = productController.getProductByName(productStr);
                    Purchase purchase = new Purchase(id, Double.parseDouble(price), time, product, quantity);
                    purchases.add(purchase);
                } catch (NumberFormatException ex) {
                    new NumberInputException();
                }
            }
            purchaseController.updatePurchases(purchases);
            reloadTable(purchaseController.getAllPurchases());
        });

        searchButton.addActionListener(e -> {
            String product = (String) productComboBox2.getSelectedItem();
            int productId;
            try {
                productId = productController.getProductByName(product).getId();
            } catch (NullPointerException ex) {
                productId = 0;
            }
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            try {
                Date fromDate = null;
                Date toDate = null;
                if (fromDateTextField.getText().equals("") && !toDateTextField.getText().equals("")) {
                    toDate = dateFormat.parse(toDateTextField.getText());
                } else if (!fromDateTextField.getText().equals("") && toDateTextField.getText().equals("")){
                    fromDate = dateFormat.parse(fromDateTextField.getText());
                } else if (!fromDateTextField.getText().equals("") && !toDateTextField.getText().equals("")) {
                    fromDate = dateFormat.parse(fromDateTextField.getText());
                    toDate = dateFormat.parse(toDateTextField.getText());
                }
                reloadTable(purchaseController.getFilteredPurchases(productId, fromDate, toDate));
            } catch (ParseException ex) {
                new DateParseException();
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
