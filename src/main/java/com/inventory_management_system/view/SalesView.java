package com.inventory_management_system.view;

import com.inventory_management_system.controller.SaleController;
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

    public SalesView() {
        AutoCompleteDecorator.decorate(productComboBox1);
        AutoCompleteDecorator.decorate(productComboBox2);
        AutoCompleteDecorator.decorate(customerComboBox1);
        AutoCompleteDecorator.decorate(customerComboBox2);
        AutoCompleteDecorator.decorate(tableProductComboBox);
        AutoCompleteDecorator.decorate(tableCustomerComboBox);
        reloadTable(saleController.getAllSales());

        reloadButton.addActionListener(e -> {
            reloadTable(saleController.getAllSales());
        });
    }

    public JPanel getSalesPanel() {
        return salesPanel;
    }

    public void reloadProduct() {
        List<Sale> sales = saleController.getAllSales();
        reloadComboBox(sales, productComboBox1, false);
        reloadComboBox(sales, productComboBox2, true);
        reloadComboBox(sales, tableProductComboBox, false);
    }

    public void reloadCustomer() {
        List<Sale> sales = saleController.getAllSales();
        reloadComboBox(sales, customerComboBox1, false);
        reloadComboBox(sales, customerComboBox2, true);
        reloadComboBox(sales, tableCustomerComboBox, false);
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
