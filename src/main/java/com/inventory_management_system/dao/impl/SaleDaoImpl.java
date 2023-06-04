package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.SaleDao;
import com.inventory_management_system.model.Customer;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Sale;
import com.inventory_management_system.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDaoImpl implements SaleDao {

    private final Connection connection;

    public SaleDaoImpl() {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from sales");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                double price = rs.getDouble("price");
                Timestamp time = rs.getTimestamp("time");
                int customerId = rs.getInt("customer_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                CustomerDaoImpl customerDao = new CustomerDaoImpl();
                Customer customer = customerDao.getCustomerById(customerId);
                ProductDaoImpl productDao = new ProductDaoImpl();
                Product product = productDao.getProductById(productId);
                Sale sale = new Sale(id, price, time, customer, product, quantity);
                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    @Override
    public Sale getSaleById(int id) {
        Sale sale = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from sales where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                double price = rs.getDouble("price");
                Timestamp time = rs.getTimestamp("time");
                int customerId = rs.getInt("customer_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                CustomerDaoImpl customerDao = new CustomerDaoImpl();
                Customer customer = customerDao.getCustomerById(customerId);
                ProductDaoImpl productDao = new ProductDaoImpl();
                Product product = productDao.getProductById(productId);
                sale = new Sale(id, price, time, customer, product, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sale;
    }

    @Override
    public void addSale(Sale sale) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into sales(price,time,customer_id,product_id,quantity,total) values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setDouble(1, sale.getPrice());
            preparedStatement.setTimestamp(2, sale.getTime());
            preparedStatement.setInt(3, sale.getCustomer().getId());
            preparedStatement.setInt(4, sale.getProduct().getId());
            preparedStatement.setInt(5, sale.getQuantity());
            preparedStatement.setDouble(6, sale.calculateTotal());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSale(Sale sale) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update sales set price=?, time=?, customer_id=?, product_id=?, quantity=?, total=? where id=?");
            preparedStatement.setDouble(1, sale.getPrice());
            preparedStatement.setTimestamp(2, sale.getTime());
            preparedStatement.setInt(3, sale.getCustomer().getId());
            preparedStatement.setInt(4, sale.getProduct().getId());
            preparedStatement.setInt(5, sale.getQuantity());
            preparedStatement.setDouble(6, sale.calculateTotal());
            preparedStatement.setInt(7, sale.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSale(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from sales where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
