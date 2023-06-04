package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.SupplierDao;
import com.inventory_management_system.exception.UnableToDeleteException;
import com.inventory_management_system.model.Supplier;
import com.inventory_management_system.model.User;
import com.inventory_management_system.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoImpl implements SupplierDao {

    private final Connection connection;

    public SupplierDaoImpl() {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from suppliers");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Supplier supplier = new Supplier(id, name, description, email, phone, address);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public List<Supplier> getFilteredSuppliers(String filterName, String filterEmail, String filterPhone, String filterAddress) {
        List<Supplier> suppliers = new ArrayList<>();
        try {
            StringBuilder stringBuilder = new StringBuilder("SELECT * FROM suppliers WHERE");
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
                String description = rs.getString("description");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Supplier supplier = new Supplier(id, name, description, email, phone, address);
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public Supplier getSupplierById(int id) {
        Supplier supplier = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from suppliers where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                supplier = new Supplier(id, name, description, email, phone, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    @Override
    public Supplier getSupplierByName(String name) {
        Supplier supplier = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from suppliers where name=?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                supplier = new Supplier(id, name, description, email, phone, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    @Override
    public void addSupplier(Supplier supplier) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into suppliers(name,description,email,phone,address) values (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getDescription());
            preparedStatement.setString(3, supplier.getEmail());
            preparedStatement.setString(4, supplier.getPhone());
            preparedStatement.setString(5, supplier.getAddress());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update suppliers set name=?, description=?, email=?, phone=?, address=? where id=?");
            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setString(2, supplier.getDescription());
            preparedStatement.setString(3, supplier.getEmail());
            preparedStatement.setString(4, supplier.getPhone());
            preparedStatement.setString(5, supplier.getAddress());
            preparedStatement.setInt(6, supplier.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSuppliers(List<Supplier> suppliers) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update suppliers set name=?, description=?, email=?, phone=?, address=? where id=?");
            for (Supplier supplier : suppliers) {
                preparedStatement.setString(1, supplier.getName());
                preparedStatement.setString(2, supplier.getDescription());
                preparedStatement.setString(3, supplier.getEmail());
                preparedStatement.setString(4, supplier.getPhone());
                preparedStatement.setString(5, supplier.getAddress());
                preparedStatement.setInt(6, supplier.getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSupplier(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from suppliers where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            new UnableToDeleteException("supplier");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesSupplierExist(Supplier supplier) {
        boolean supplierExists = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from suppliers where id<>? and name=?");
            preparedStatement.setInt(1, supplier.getId());
            preparedStatement.setString(2, supplier.getName());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                supplierExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierExists;
    }
}