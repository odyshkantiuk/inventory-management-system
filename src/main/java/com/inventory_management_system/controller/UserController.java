package com.inventory_management_system.controller;

import com.inventory_management_system.dao.UserDao;
import com.inventory_management_system.dao.impl.UserDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
import com.inventory_management_system.exception.LoginException;
import com.inventory_management_system.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserController {
    private final UserDao userDao;

    public UserController() {
        userDao = new UserDaoImpl();
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public List<User> getFilteredUsers(String filterName, String filterRole) {
        return userDao.getFilteredUsers(filterName, filterRole);
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
            new LoginException();
        }
        return user;
    }

    public void addUser(User user) {
        if (!userDao.doesUserExist(user)) {
            userDao.addUser(user);
        } else {
            new AlreadyExistsException("User");
        }
    }

    public void updateUser(User user) {
        if (!userDao.doesUserExist(user)) {
            userDao.updateUser(user);
        } else {
            new AlreadyExistsException("User");
        }
    }

    public void updateUsers(List<User> users) {
        Set<String> nameSet = new HashSet<>();
        for (User user : users){
            if (userDao.doesUserExist(user)) {
                new AlreadyExistsException("User");
                return;
            } else if (!nameSet.add(user.getName())) {
                new AlreadyExistsException("User");
                return;
            }
        }
        userDao.updateUsers(users);
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }
}
