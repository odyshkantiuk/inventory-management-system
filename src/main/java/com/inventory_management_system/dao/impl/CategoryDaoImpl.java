package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.CategoryDao;
import com.inventory_management_system.exception.TooLongException;
import com.inventory_management_system.exception.UnableToDeleteException;
import com.inventory_management_system.model.Category;
import com.inventory_management_system.util.DBUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Category dao implementation.
 *
 * @author Oleksandr Dyshkantiuk
 */
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
    public Category getCategoryByName(String name) {
        Category category = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from categories where name=?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
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
        } catch (MysqlDataTruncation e) {
            new TooLongException();
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
        } catch (MysqlDataTruncation e) {
            new TooLongException();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCategory(String name) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from categories where name=?");
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            new UnableToDeleteException("category");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesCategoryExist(Category category) {
        boolean categoryExists = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from categories where id<>? and name=?");
            preparedStatement.setInt(1, category.getId());
            preparedStatement.setString(2, category.getName());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                categoryExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryExists;
    }
}