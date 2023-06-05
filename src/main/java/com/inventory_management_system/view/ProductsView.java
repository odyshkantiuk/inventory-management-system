package com.inventory_management_system.view;

import com.inventory_management_system.controller.CategoryController;
import com.inventory_management_system.controller.ProductController;
import com.inventory_management_system.controller.PurchaseController;
import com.inventory_management_system.controller.SupplierController;
import com.inventory_management_system.exception.NumberInputException;
import com.inventory_management_system.model.Category;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Purchase;
import com.inventory_management_system.model.Supplier;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 * The ProductsView class represents the graphical user interface for managing products in the inventory management system.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class ProductsView {
    private JPanel productsPanel;
    private JTabbedPane tabbedPane1;
    private JTextField nameTextField1;
    private JTextField descriptionTextField;
    private JTextField purchasePriceTextField;
    private JTextField salePriceTextField;
    private JTextField quantityTextField;
    private JComboBox categoryComboBox1;
    private JComboBox supplierComboBox1;
    private JButton addProductButton;
    private JTextField categoryNameTextField;
    private JTextField categoryDescriptionTextField;
    private JButton addCategoryButton;
    private JComboBox categoryComboBox3;
    private JButton deleteCategoryButton;
    private JTextField nameTextField2;
    private JComboBox categoryComboBox2;
    private JComboBox supplierComboBox2;
    private JButton searchButton;
    private JButton reloadButton;
    private JTable table;
    private JButton deleteButton;
    private JButton applyButton;
    private JButton moreInfoButton;
    private final JComboBox tableCategoryComboBox = new JComboBox<>();
    private final JComboBox tableSupplierComboBox = new JComboBox<>();

    private final CategoryController categoryController = new CategoryController();
    private final SupplierController supplierController = new SupplierController();
    private final ProductController productController = new ProductController();
    private final PurchaseController purchaseController = new PurchaseController();

    public ProductsView() {
        AutoCompleteDecorator.decorate(supplierComboBox1);
        AutoCompleteDecorator.decorate(supplierComboBox2);
        AutoCompleteDecorator.decorate(categoryComboBox1);
        AutoCompleteDecorator.decorate(categoryComboBox2);
        AutoCompleteDecorator.decorate(categoryComboBox3);
        AutoCompleteDecorator.decorate(tableSupplierComboBox);
        AutoCompleteDecorator.decorate(tableCategoryComboBox);
        reloadSuppliers();
        reloadCategory();
        reloadTable(productController.getAllProducts());

        addCategoryButton.addActionListener(e -> {
            String name = categoryNameTextField.getText();
            String description = categoryDescriptionTextField.getText();
            categoryController.addCategory(new Category(0, name, description));
            reloadCategory();
        });

        deleteCategoryButton.addActionListener(e -> {
            String name = (String) categoryComboBox3.getSelectedItem();
            categoryController.deleteCategory(name);
            reloadCategory();
        });

        addProductButton.addActionListener(e -> {
            String name = nameTextField1.getText();
            String description = descriptionTextField.getText();
            try {
                double purchasePrice = Double.parseDouble(purchasePriceTextField.getText());
                double salePrice = Double.parseDouble(salePriceTextField.getText());
                int quantity = Integer.parseInt(quantityTextField.getText());
                String category = (String) categoryComboBox1.getSelectedItem();
                String supplier = (String) supplierComboBox1.getSelectedItem();
                Product product = new Product(0, name, description, purchasePrice, salePrice, quantity, categoryController.getCategoryByName(category), supplierController.getSupplierByName(supplier));
                if (productController.addProduct(product)){
                    Timestamp time = new Timestamp(System.currentTimeMillis());
                    product.setId(productController.getProductByName(name).getId());
                    purchaseController.addPurchase(new Purchase(0, purchasePrice, time, product, quantity));
                }
                reloadTable(productController.getAllProducts());
            } catch (NumberFormatException ex) {
                new NumberInputException();
            }
        });

        reloadButton.addActionListener(e -> reloadTable(productController.getAllProducts()));

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                productController.deleteProduct((Integer) table.getValueAt(selectedRow, 0));
                reloadTable(productController.getAllProducts());
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                moreInfoButton.setVisible(column == 2);
            }
        });

        moreInfoButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            int id = (int) table.getValueAt(selectedRow, 0);
            new InfoView(productController.getProductById(id).getDescription());
        });

        applyButton.addActionListener(e -> {
            List<Product> products = new ArrayList<>();
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            int rowCount = tableModel.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                int id = (Integer) tableModel.getValueAt(i, 0);
                String name = (String) tableModel.getValueAt(i, 1);
                String description = (String) tableModel.getValueAt(i, 2);
                String purchasePrice = (String) tableModel.getValueAt(i, 3);
                String salePrice = (String) tableModel.getValueAt(i, 4);
                String quantity = (String) tableModel.getValueAt(i, 5);
                String category = (String) tableModel.getValueAt(i, 6);
                String supplier = (String) tableModel.getValueAt(i, 7);
                try {
                    products.add(new Product(id, name, description, Double.parseDouble(purchasePrice), Double.parseDouble(salePrice), Integer.parseInt(quantity), categoryController.getCategoryByName(category), supplierController.getSupplierByName(supplier)));
                } catch (NumberFormatException ex) {
                    new NumberInputException();
                }
            }
            productController.updateProducts(products);
            reloadTable(productController.getAllProducts());
        });

        searchButton.addActionListener(e -> {
            String name = nameTextField2.getText();
            String category = (String) categoryComboBox2.getSelectedItem();
            String supplier = (String) supplierComboBox2.getSelectedItem();
            int categoryId = 0;
            int supplierId = 0;
            if (category.equals("") && !supplier.equals("")) {
                supplierId = supplierController.getSupplierByName(supplier).getId();
            } else if (supplier.equals("") && !category.equals("")) {
                categoryId = categoryController.getCategoryByName(category).getId();
            } else if (!category.equals("") && !supplier.equals("")) {
                supplierId = supplierController.getSupplierByName(supplier).getId();
                categoryId = categoryController.getCategoryByName(category).getId();
            }
            reloadTable(productController.getFilteredProducts(name, categoryId, supplierId));
        });
    }

    public void reloadSuppliers() {
        List<Supplier> suppliers = supplierController.getAllSuppliers();
        reloadComboBox(suppliers, supplierComboBox1, false);
        reloadComboBox(suppliers, supplierComboBox2, true);
        reloadComboBox(suppliers, tableSupplierComboBox, false);
    }

    public void reloadCategory() {
        List<Category> categories = categoryController.getAllCategories();
        reloadComboBox(categories, categoryComboBox1, false);
        reloadComboBox(categories, categoryComboBox2, true);
        reloadComboBox(categories, categoryComboBox3, false);
        reloadComboBox(categories, tableCategoryComboBox, false);
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

    public JPanel getProductsPanel() {
        return productsPanel;
    }

    public void reloadTable(List<Product> products) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        model.addColumn("id");
        model.addColumn("Name");
        model.addColumn("Description");
        model.addColumn("Purchase price");
        model.addColumn("Sale price");
        model.addColumn("Quantity");
        model.addColumn("Category");
        model.addColumn("Supplier");
        for (Product product : products) {
            Object[] rowData = {product.getId(), product.getName(), product.getDescription(), String.valueOf(product.getPurchasePrice()), String.valueOf(product.getSalePrice()), String.valueOf(product.getQuantity()), product.getCategory().getName(), product.getSupplier().getName()};
            model.addRow(rowData);
        }
        table.setModel(model);
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(6).setCellRenderer(new ComboBoxCellRenderer(tableCategoryComboBox));
        columnModel.getColumn(6).setCellEditor(new DefaultCellEditor(tableCategoryComboBox));
        columnModel.getColumn(7).setCellRenderer(new ComboBoxCellRenderer(tableSupplierComboBox));
        columnModel.getColumn(7).setCellEditor(new DefaultCellEditor(tableSupplierComboBox));
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
