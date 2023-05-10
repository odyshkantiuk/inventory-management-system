package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.PurchasedProductDao;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.PurchasedProduct;
import com.inventory_management_system.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchasedProductDaoImpl implements PurchasedProductDao {

    private final Connection connection;

    public PurchasedProductDaoImpl() {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<PurchasedProduct> getAllPurchasedProducts() {
        List<PurchasedProduct> purchasedProducts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from purchased_products");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                ProductDaoImpl productDao = new ProductDaoImpl();
                Product product = productDao.getProductById(productId);
                PurchasedProduct purchasedProduct = new PurchasedProduct(id, product, quantity);
                purchasedProducts.add(purchasedProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchasedProducts;
    }

    @Override
    public PurchasedProduct getPurchasedProductById(int id) {
        PurchasedProduct purchasedProduct = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from purchased_products where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                ProductDaoImpl productDao = new ProductDaoImpl();
                Product product = productDao.getProductById(productId);
                purchasedProduct = new PurchasedProduct(id, product, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchasedProduct;
    }

    @Override
    public void addPurchasedProduct(PurchasedProduct purchasedProduct) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into purchased_products(product_id,quantity,total) values (?, ?, ?)");
            preparedStatement.setInt(1, purchasedProduct.getProduct().getId());
            preparedStatement.setInt(2, purchasedProduct.getQuantity());
            preparedStatement.setDouble(3, purchasedProduct.calculateTotal());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePurchasedProduct(PurchasedProduct purchasedProduct) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update purchased_products set product_id=?, quantity=?, total=? where id=?");
            preparedStatement.setInt(1, purchasedProduct.getProduct().getId());
            preparedStatement.setInt(2, purchasedProduct.getQuantity());
            preparedStatement.setDouble(3, purchasedProduct.calculateTotal());
            preparedStatement.setInt(4, purchasedProduct.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePurchasedProduct(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from purchased_products where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
