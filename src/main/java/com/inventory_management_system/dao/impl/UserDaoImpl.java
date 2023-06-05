package com.inventory_management_system.dao.impl;

import com.inventory_management_system.dao.UserDao;
import com.inventory_management_system.exception.TooLongException;
import com.inventory_management_system.model.User;
import com.inventory_management_system.util.DBUtil;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * User dao implementation.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class UserDaoImpl implements UserDao {

    private final Connection connection;

    public UserDaoImpl() {
        connection = DBUtil.getConnection();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");
                User user = new User(id, name, password, role);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getFilteredUsers(String filterName, String filterRole) {
        List<User> users = new ArrayList<>();
        try {
            StringBuilder stringBuilder = new StringBuilder("select * from users where");
            List<String> conditions = new ArrayList<>();
            List<Object> parameters = new ArrayList<>();
            if (filterName != null && !filterName.isEmpty()) {
                conditions.add("name like ?");
                parameters.add("%" + filterName + "%");
            }
            if (filterRole != null && !filterRole.isEmpty()) {
                conditions.add("role = ?");
                parameters.add(filterRole);
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
                String password = rs.getString("password");
                String role = rs.getString("role");
                User user = new User(id, name, password, role);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id=?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                String password = rs.getString("password");
                String role = rs.getString("role");
                user = new User(id, name, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserByRole(String role) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where role=?");
            preparedStatement.setString(1, role);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                user = new User(id, name, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getUser(String name, String password) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where name=? and password=?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String role = rs.getString("role");
                user = new User(id, name, password, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users(name,password,role) values (?, ?, ?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.executeUpdate();
        } catch (MysqlDataTruncation e) {
            new TooLongException();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set name=?, password=?, role=? where id=?");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getRole());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (MysqlDataTruncation e) {
            new TooLongException();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUsers(List<User> users) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set name=?, password=?, role=? where id=?");
            for (User user : users) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getRole());
                preparedStatement.setInt(4, user.getId());
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
    public void deleteUser(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesUserExist(User user) {
        boolean userExists = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id<>? and name=?");
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setString(2, user.getName());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                userExists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userExists;
    }
}