package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.PurchaseDao;
import com.inventory_management_system.exception.TooLongException;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Purchase;
import com.inventory_management_system.util.DBUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDaoImpl implements PurchaseDao {

    private final Connection connection;

    public PurchaseDaoImpl() {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<Purchase> getAllPurchases() {
        List<Purchase> purchases = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from purchases");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                double price = rs.getDouble("price");
                Timestamp time = rs.getTimestamp("time");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                ProductDaoImpl productDao = new ProductDaoImpl();
                Product product = productDao.getProductById(productId);
                Purchase purchase = new Purchase(id, price, time, product, quantity);
                purchases.add(purchase);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchases;
    }

    @Override
    public Purchase getPurchaseById(int id) {
        Purchase purchase = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from purchases where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                double price = rs.getDouble("price");
                Timestamp time = rs.getTimestamp("time");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                ProductDaoImpl productDao = new ProductDaoImpl();
                Product product = productDao.getProductById(productId);
                purchase = new Purchase(id, price, time, product, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchase;
    }

    @Override
    public void addPurchase(Purchase purchase) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into purchases(price, time,product_id,quantity,total) values (?, ?, ?, ?, ?)");
            preparedStatement.setDouble(1, purchase.getPrice());
            preparedStatement.setTimestamp(2, purchase.getTime());
            preparedStatement.setInt(3, purchase.getProduct().getId());
            preparedStatement.setInt(4, purchase.getQuantity());
            preparedStatement.setDouble(5, purchase.calculateTotal());
            preparedStatement.executeUpdate();
        } catch (MysqlDataTruncation e) {
            new TooLongException();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePurchase(Purchase purchase) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update purchases set price=?, time=?, product_id=?, quantity=?, total=? where id=?");
            preparedStatement.setDouble(1, purchase.getPrice());
            preparedStatement.setTimestamp(2, purchase.getTime());
            preparedStatement.setInt(3, purchase.getProduct().getId());
            preparedStatement.setInt(4, purchase.getQuantity());
            preparedStatement.setDouble(5, purchase.calculateTotal());
            preparedStatement.setInt(6, purchase.getId());
            preparedStatement.executeUpdate();
        } catch (MysqlDataTruncation e) {
            new TooLongException();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePurchases(List<Purchase> purchases) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update purchases set price=?, time=?, product_id=?, quantity=?, total=? where id=?");
            for (Purchase purchase : purchases) {
                preparedStatement.setDouble(1, purchase.getPrice());
                preparedStatement.setTimestamp(2, purchase.getTime());
                preparedStatement.setInt(3, purchase.getProduct().getId());
                preparedStatement.setInt(4, purchase.getQuantity());
                preparedStatement.setDouble(5, purchase.calculateTotal());
                preparedStatement.setInt(6, purchase.getId());
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
    public void deletePurchase(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from purchases where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
