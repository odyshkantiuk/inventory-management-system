package com.inventory_management_system.dao;

import java.util.List;
import com.inventory_management_system.model.User;

public interface UserDao {
    public List<User> getAllUsers();
    public User getUserById(int id);
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(int id);
}