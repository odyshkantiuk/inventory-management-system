package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.CategoryDao;
import com.inventory_management_system.model.Category;
import com.inventory_management_system.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {

    private final Connection connection;

    public CategoryDaoImpl() {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from categories");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                Category category = new Category(id, name, description);
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getCategoryById(int id) {
        Category category = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from categories where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                category = new Category(id, name, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public void addCategory(Category category) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into categories(name,description) values (?, ?)");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCategory(Category category) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update categories set name=?, description where id=?");
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3, category.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from categories where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}