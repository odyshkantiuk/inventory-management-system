package com.inventory_management_system.dao;

import java.util.List;
import com.inventory_management_system.model.User;

public interface UserDao {
    List<User> getAllUsers();
    User getUserById(int id);
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
}