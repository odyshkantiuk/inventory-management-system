package com.inventory_management_system.model;
/**
 * User model class.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String role;

    /**
     * Constructs a User object with the specified id, name, password, and role.
     *
     * @param id       the unique identifier for the user
     * @param name     the name of the user
     * @param password the password of the user
     * @param role     the role of the user
     */
    public User(int id, String name, String password, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
