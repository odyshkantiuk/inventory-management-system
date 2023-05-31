package com.inventory_management_system.controller;

import com.inventory_management_system.dao.UserDao;
import com.inventory_management_system.dao.impl.UserDaoImpl;
import com.inventory_management_system.exception.LoginException;
import com.inventory_management_system.model.User;

import java.util.List;

public class UserController {
    private final UserDao userDao;

    public UserController() {
        userDao = new UserDaoImpl();
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    public User getUserByRole(String role) {
        return userDao.getUserByRole(role);
    }

    public User getUser(String name, String password) {
        User user = userDao.getUser(name, password);
        if(user == null) {
            LoginException loginException = new LoginException();
        } else {

        }
        return user;
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }
}
