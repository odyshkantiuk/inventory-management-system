package com.inventory_management_system.controller;

import com.inventory_management_system.dao.UserDao;
import com.inventory_management_system.dao.impl.UserDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
import com.inventory_management_system.exception.LoginException;
import com.inventory_management_system.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * The UserController class handles operations related to user management.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class UserController {
    private final UserDao userDao;

    /**
     * Constructs a UserController object and initializes the UserDao.
     */
    public UserController() {
        userDao = new UserDaoImpl();
    }

    /**
     * Retrieves all users.
     *
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    /**
     * Retrieves filtered users based on provided filters.
     *
     * @param filterName The name filter.
     * @param filterRole The role filter.
     * @return A list of filtered users.
     */
    public List<User> getFilteredUsers(String filterName, String filterRole) {
        return userDao.getFilteredUsers(filterName, filterRole);
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user with the specified ID.
     */
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    /**
     * Retrieves a user by its role.
     *
     * @param role The role of the user to retrieve.
     * @return The user with the specified role.
     */
    public User getUserByRole(String role) {
        return userDao.getUserByRole(role);
    }

    /**
     * Retrieves a user by its name and password.
     *
     * @param name     The name of the user.
     * @param password The password of the user.
     * @return The user with the specified name and password.
     * @throws LoginException If the login credentials are incorrect.
     */
    public User getUser(String name, String password) {
        User user = userDao.getUser(name, password);
        if(user == null) {
            new LoginException();
        }
        return user;
    }

    /**
     * Adds a new user.
     *
     * @param user The user to add.
     * @throws AlreadyExistsException If a user with the same details already exists.
     */
    public void addUser(User user) {
        if (!userDao.doesUserExist(user)) {
            userDao.addUser(user);
        } else {
            new AlreadyExistsException("User");
        }
    }

    /**
     * Updates an existing user.
     *
     * @param user The user to update.
     * @throws AlreadyExistsException If a user with the same details already exists.
     */
    public void updateUser(User user) {
        if (!userDao.doesUserExist(user)) {
            userDao.updateUser(user);
        } else {
            new AlreadyExistsException("User");
        }
    }

    /**
     * Updates multiple users.
     *
     * @param users A list of users to update.
     * @throws AlreadyExistsException If a user with the same details already exists.
     */
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

    /**
     * Deletes a user by its ID.
     *
     * @param id The ID of the user to delete.
     */
    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }
}
