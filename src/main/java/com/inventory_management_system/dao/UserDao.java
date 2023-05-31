package com.inventory_management_system.dao;

import com.inventory_management_system.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    User getUserById(int id);
    User getUserByRole(String role);
    User getUser(String name, String password);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
}