package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.ProductDao;
import com.inventory_management_system.dao.impl.CategoryDaoImpl;
import com.inventory_management_system.model.Category;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Supplier;
import com.inventory_management_system.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao {

    private final Connection connection;

    public ProductDaoImpl() {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from products");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double purchasePrice = rs.getDouble("purchase_price");
                double salePrice = rs.getDouble("sale_price");
                int quantity = rs.getInt("quantity");
                int categoryId = rs.getInt("category_id");
                int supplierId = rs.getInt("supplier_id");
                CategoryDaoImpl categoryDao = new CategoryDaoImpl();
                Category category = categoryDao.getCategoryById(categoryId);
                SupplierDaoImpl supplierDao = new SupplierDaoImpl();
                Supplier supplier = supplierDao.getSupplierById(supplierId);
                Product product = new Product(id, name, description, purchasePrice, salePrice, quantity, category, supplier);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product getProductById(int id) {
        Product product = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from products where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                double purchasePrice = rs.getDouble("purchase_price");
                double salePrice = rs.getDouble("sale_price");
                int quantity = rs.getInt("quantity");
                int categoryId = rs.getInt("category_id");
                int supplierId = rs.getInt("supplier_id");
                CategoryDaoImpl categoryDao = new CategoryDaoImpl();
                Category category = categoryDao.getCategoryById(categoryId);
                SupplierDaoImpl supplierDao = new SupplierDaoImpl();
                Supplier supplier = supplierDao.getSupplierById(supplierId);
                product = new Product(id, name, description, purchasePrice, salePrice, quantity, category, supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void addProduct(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into products(name,description,purchase_price,sale_price,quantity,category_id,supplier_id) values (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPurchasePrice());
            preparedStatement.setDouble(4, product.getSalePrice());
            preparedStatement.setInt(5, product.getQuantity());
            preparedStatement.setInt(6, product.getCategory().getId());
            preparedStatement.setInt(7, product.getSupplier().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update products set name=?, description=?, purchase_price=?, sale_price=?, quantity=?, category_id=?, supplier_id=? where id=?");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPurchasePrice());
            preparedStatement.setDouble(4, product.getSalePrice());
            preparedStatement.setInt(5, product.getQuantity());
            preparedStatement.setInt(6, product.getCategory().getId());
            preparedStatement.setInt(7, product.getSupplier().getId());
            preparedStatement.setInt(8, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteProduct(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from products where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}