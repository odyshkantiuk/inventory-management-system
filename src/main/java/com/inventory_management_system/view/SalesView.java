package com.inventory_management_system.view;

import com.inventory_management_system.controller.CustomerController;
import com.inventory_management_system.controller.ProductController;
import com.inventory_management_system.controller.SaleController;
import com.inventory_management_system.exception.DateParseException;
import com.inventory_management_system.exception.NumberInputException;
import com.inventory_management_system.model.Customer;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Purchase;
import com.inventory_management_system.model.Sale;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesView {
    private JPanel salesPanel;
    private JComboBox productComboBox1;
    private JTextField priceTextField;
    private JTextField quantityTextField;
    private JComboBox customerComboBox1;
    private JButton addSaleButton;
    private JComboBox productComboBox2;
    private JComboBox customerComboBox2;
    private JTextField fromDateTextField;
    private JTextField toDateTextField;
    private JButton searchButton;
    private JButton reloadButton;
    private JButton deleteButton;
    private JButton applyButton;
    private JTable table;
    private final JComboBox tableProductComboBox = new JComboBox<>();
    private final JComboBox tableCustomerComboBox = new JComboBox<>();

    private final SaleController saleController = new SaleController();
    private final ProductController productController = new ProductController();
    private final CustomerController customerController = new CustomerController();

    public SalesView() {
        AutoCompleteDecorator.decorate(productComboBox1);
        AutoCompleteDecorator.decorate(productComboBox2);
        AutoCompleteDecorator.decorate(customerComboBox1);
        AutoCompleteDecorator.decorate(customerComboBox2);
        AutoCompleteDecorator.decorate(tableProductComboBox);
        AutoCompleteDecorator.decorate(tableCustomerComboBox);
        reloadTable(saleController.getAllSales());

        reloadButton.addActionListener(e -> reloadTable(saleController.getAllSales()));

        productComboBox1.addActionListener(e -> {
            Product product = productController.getProductByName((String) productComboBox1.getSelectedItem());
            if (product != null) {
                priceTextField.setText(String.valueOf(product.getSalePrice()));
            }
        });

        tableProductComboBox.addActionListener(e -> {
            Product product = productController.getProductByName((String) tableProductComboBox.getSelectedItem());
            if (product != null) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    table.setValueAt(String.valueOf(product.getSalePrice()), selectedRow, 3);
                }
            }
        });

        addSaleButton.addActionListener(e -> {
            Product product = productController.getProductByName((String) productComboBox1.getSelectedItem());
            Customer customer = customerController.getCustomerByName((String) customerComboBox1.getSelectedItem());
            try {
                double price = Double.parseDouble(priceTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());
                Timestamp time = new Timestamp(System.currentTimeMillis());
                Sale sale = new Sale(0, price, time, customer, product, quantity);
                saleController.addSale(sale);
                reloadTable(saleController.getAllSales());
            } catch (NumberFormatException ex) {
                new NumberInputException();
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                saleController.deleteSale((Integer) table.getValueAt(selectedRow, 0));
                reloadTable(saleController.getAllSales());
            }
        });

        applyButton.addActionListener(e -> {
            List<Sale> sales = new ArrayList<>();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                int id = (Integer) tableModel.getValueAt(i, 0);
                String productStr = (String) tableModel.getValueAt(i, 1);
                Timestamp time = (Timestamp) tableModel.getValueAt(i, 2);
                String price = (String) tableModel.getValueAt(i, 3);
                String quantityStr = (String) tableModel.getValueAt(i, 4);
                String customerStr = (String) tableModel.getValueAt(i, 5);
                try {
                    int quantity = Integer.parseInt(quantityStr);
                    Product product = productController.getProductByName(productStr);
                    Customer customer = customerController.getCustomerByName(customerStr);
                    Sale sale = new Sale(id, Double.parseDouble(price), time, customer, product, quantity);
                    sales.add(sale);
                } catch (NumberFormatException ex) {
                    new NumberInputException();
                }
            }
            saleController.updateSales(sales);
            reloadTable(saleController.getAllSales());
        });

        searchButton.addActionListener(e -> {
            String product = (String) productComboBox2.getSelectedItem();
            int productId;
            try {
                productId = productController.getProductByName(product).getId();
            } catch (NullPointerException ex) {
                productId = 0;
            }
            String customer = (String) customerComboBox2.getSelectedItem();
            int customerId;
            try {
                customerId = customerController.getCustomerByName(customer).getId();
            } catch (NullPointerException ex) {
                customerId = 0;
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
                reloadTable(saleController.getFilteredSales(productId, customerId, fromDate, toDate));
            } catch (ParseException ex) {
                new DateParseException();
            }
        });
    }

    public JPanel getSalesPanel() {
        return salesPanel;
    }

    public void reloadProduct() {
        List<Product> products = productController.getAllProducts();
        reloadComboBox(products, productComboBox1, false);
        reloadComboBox(products, productComboBox2, true);
        reloadComboBox(products, tableProductComboBox, false);
    }

    public void reloadCustomer() {
        List<Customer> customers = customerController.getAllCustomers();
        reloadComboBox(customers, customerComboBox1, false);
        reloadComboBox(customers, customerComboBox2, true);
        reloadComboBox(customers, tableCustomerComboBox, false);
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

    public void reloadTable(List<Sale> sales) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && column != 2 && column != 6;
            }
        };
        model.addColumn("id");
        model.addColumn("Product");
        model.addColumn("Time");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Customer");
        model.addColumn("Total");
        for (Sale sale : sales) {
            Object[] rowData = {sale.getId(), sale.getProduct().getName(), sale.getTime(), String.valueOf(sale.getPrice()), String.valueOf(sale.getQuantity()), sale.getCustomer().getName(), sale.calculateTotal()};
            model.addRow(rowData);
        }
        table.setModel(model);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(1).setCellRenderer(new ComboBoxCellRenderer(tableProductComboBox));
        columnModel.getColumn(1).setCellEditor(new DefaultCellEditor(tableProductComboBox));
        columnModel.getColumn(5).setCellRenderer(new ComboBoxCellRenderer(tableCustomerComboBox));
        columnModel.getColumn(5).setCellEditor(new DefaultCellEditor(tableCustomerComboBox));
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
