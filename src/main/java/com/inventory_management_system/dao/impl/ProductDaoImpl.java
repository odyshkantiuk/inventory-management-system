package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.ProductDao;
import com.inventory_management_system.exception.TooLongException;
import com.inventory_management_system.model.Category;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Supplier;
import com.inventory_management_system.util.DBUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Product dao implementation.
 *
 * @author Oleksandr Dyshkantiuk
 */
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
    public List<Product> getFilteredProducts(String filterName, int filterCategory, int filterSupplier) {
        List<Product> products = new ArrayList<>();
        try {
            StringBuilder stringBuilder = new StringBuilder("select * from products where");
            List<String> conditions = new ArrayList<>();
            List<Object> parameters = new ArrayList<>();
            if (filterName != null && !filterName.isEmpty()) {
                conditions.add("name like ?");
                parameters.add("%" + filterName + "%");
            }
            if (filterCategory > 0) {
                conditions.add("category_id = ?");
                parameters.add(filterCategory);
            }
            if (filterSupplier > 0) {
                conditions.add("supplier_id = ?");
                parameters.add(filterSupplier);
            }

            if (!conditions.isEmpty()) {
                String conditionsStr = String.join(" and ", conditions);
                stringBuilder.append(" ").append(conditionsStr);
            } else {
                stringBuilder.append(" 1");
            }

            PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

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
    public Product getProductByName(String name) {
        Product product = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from products where name=?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
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
    public boolean addProduct(Product product) {
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
            return true;
        } catch (MysqlDataTruncation e) {
            new TooLongException();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
        } catch (MysqlDataTruncation e) {
            new TooLongException();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProducts(List<Product> products) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update products set name=?, description=?, purchase_price=?, sale_price=?, quantity=?, category_id=?, supplier_id=? where id=?");
            for (Product product : products) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setString(2, product.getDescription());
                preparedStatement.setDouble(3, product.getPurchasePrice());
                preparedStatement.setDouble(4, product.getSalePrice());
                preparedStatement.setInt(5, product.getQuantity());
                preparedStatement.setInt(6, product.getCategory().getId());
                preparedStatement.setInt(7, product.getSupplier().getId());
                preparedStatement.setInt(8, product.getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (MysqlDataTruncation e) {
            new TooLongException();
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

    @Override
    public boolean doesProductExist(Product product) {
        boolean productExists = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from products where id<>? and name=?");
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                productExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productExists;
    }
}