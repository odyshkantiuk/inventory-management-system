package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.CustomerDao;
import com.inventory_management_system.exception.TooLongException;
import com.inventory_management_system.model.Customer;
import com.inventory_management_system.model.Supplier;
import com.inventory_management_system.util.DBUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {

    private final Connection connection;

    public CustomerDaoImpl() {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customers");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Customer customer = new Customer(id, name, email, phone, address);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public List<Customer> getFilteredCustomers(String filterName, String filterEmail, String filterPhone, String filterAddress) {
        List<Customer> customers = new ArrayList<>();
        try {
            StringBuilder stringBuilder = new StringBuilder("select * from customers where");
            List<String> conditions = new ArrayList<>();
            List<Object> parameters = new ArrayList<>();

            if (filterName != null && !filterName.isEmpty()) {
                conditions.add("name LIKE ?");
                parameters.add("%" + filterName + "%");
            }

            if (filterEmail != null && !filterEmail.isEmpty()) {
                conditions.add("email LIKE ?");
                parameters.add("%" + filterEmail + "%");
            }

            if (filterPhone != null && !filterPhone.isEmpty()) {
                conditions.add("phone LIKE ?");
                parameters.add("%" + filterPhone + "%");
            }

            if (filterAddress != null && !filterAddress.isEmpty()) {
                conditions.add("address LIKE ?");
                parameters.add("%" + filterAddress + "%");
            }

            if (!conditions.isEmpty()) {
                String conditionsStr = String.join(" AND ", conditions);
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
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Customer customer = new Customer(id, name, email, phone, address);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }


    @Override
    public Customer getCustomerById(int id) {
        Customer customer = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customers where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                customer = new Customer(id, name, email, phone, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void addCustomer(Customer customer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into customers(name,email,phone,address) values (?, ?, ?, ?)");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update customers set name=?, email=?, phone=?, address=? where id=?");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getPhone());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setInt(5, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCustomers(List<Customer> customers) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update customers set name=?, email=?, phone=?, address=? where id=?");
            for (Customer customer : customers) {
                preparedStatement.setString(1, customer.getName());
                preparedStatement.setString(2, customer.getEmail());
                preparedStatement.setString(3, customer.getPhone());
                preparedStatement.setString(4, customer.getAddress());
                preparedStatement.setInt(5, customer.getId());
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
    public void deleteCustomer(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from customers where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesCustomerExist(Customer customer) {
        boolean customerExist = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from customers where id<>? and name=?");
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                customerExist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerExist;
    }
}