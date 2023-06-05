package com.inventory_management_system.dao;

import com.inventory_management_system.model.User;

import java.util.List;
/**
 * User dao interface.
 *
 * @author Oleksandr Dyshkantiuk
 */
public interface UserDao {
    /**
     * Retrieves all users from the data source.
     *
     * @return a list of all users
     */
    List<User> getAllUsers();

    /**
     * Retrieves filtered users from the data source based on the provided filters.
     *
     * @param filterName the name filter
     * @param filterRole the role filter
     * @return a list of filtered users
     */
    List<User> getFilteredUsers(String filterName, String filterRole);

    /**
     * Retrieves a user from the data source based on its id.
     *
     * @param id the id of the user to retrieve
     * @return the user with the specified id, or null if not found
     */
    User getUserById(int id);

    /**
     * Retrieves a user from the data source based on its role.
     *
     * @param role the role of the user to retrieve
     * @return the user with the specified role, or null if not found
     */
    User getUserByRole(String role);

    /**
     * Retrieves a user from the data source based on its name and password.
     *
     * @param name     the name of the user
     * @param password the password of the user
     * @return the user with the specified name and password, or null if not found
     */
    User getUser(String name, String password);

    /**
     * Adds a new user to the data source.
     *
     * @param user the user to add
     */
    void addUser(User user);

    /**
     * Updates an existing user in the data source.
     *
     * @param user the user to update
     */
    void updateUser(User user);

    /**
     * Updates multiple users in the data source.
     *
     * @param users the list of users to update
     */
    void updateUsers(List<User> users);

    /**
     * Deletes a user from the data source based on its id.
     *
     * @param id the id of the user to delete
     */
    void deleteUser(int id);

    /**
     * Checks if a user exists in the data source.
     *
     * @param user the user to check
     * @return true if the user exists, false otherwise
     */
    boolean doesUserExist(User user);
}