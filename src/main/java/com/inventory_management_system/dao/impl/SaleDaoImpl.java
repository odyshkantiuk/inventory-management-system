package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.SaleDao;
import com.inventory_management_system.model.Customer;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Sale;
import com.inventory_management_system.utils.DBUtil;

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
                Timestamp time = rs.getTimestamp("time");
                int customerId = rs.getInt("customer_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                CustomerDaoImpl customerDao = new CustomerDaoImpl();
                Customer customer = customerDao.getCustomerById(customerId);
                ProductDaoImpl productDao = new ProductDaoImpl();
                Product product = productDao.getProductById(productId);
                Sale sale = new Sale(id, time, customer, product, quantity);
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
                Timestamp time = rs.getTimestamp("time");
                int customerId = rs.getInt("customer_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                CustomerDaoImpl customerDao = new CustomerDaoImpl();
                Customer customer = customerDao.getCustomerById(customerId);
                ProductDaoImpl productDao = new ProductDaoImpl();
                Product product = productDao.getProductById(productId);
                sale = new Sale(id, time, customer, product, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sale;
    }

    @Override
    public void addSale(Sale sale) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into sales(time,customer_id,product_id,quantity,total) values (?, ?, ?, ?, ?)");
            preparedStatement.setTimestamp(1, sale.getTime());
            preparedStatement.setInt(2, sale.getCustomer().getId());
            preparedStatement.setInt(3, sale.getProduct().getId());
            preparedStatement.setInt(4, sale.getQuantity());
            preparedStatement.setDouble(5, sale.calculateTotal());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSale(Sale sale) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update sales set time=?, customer_id=?, product_id=?, quantity=?, total=? where id=?");
            preparedStatement.setTimestamp(1, sale.getTime());
            preparedStatement.setInt(2, sale.getCustomer().getId());
            preparedStatement.setInt(3, sale.getProduct().getId());
            preparedStatement.setInt(4, sale.getQuantity());
            preparedStatement.setDouble(5, sale.calculateTotal());
            preparedStatement.setInt(6, sale.getId());
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
