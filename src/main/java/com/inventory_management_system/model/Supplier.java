package com.inventory_management_system.model;
/**
 * Supplier model class.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class Supplier {
    private int id;
    private String name;
    private String description;
    private String email;
    private String phone;
    private String address;

    /**
     * Constructs a Supplier object with the specified id, name, description, email, phone, and address.
     *
     * @param id          the unique identifier for the supplier
     * @param name        the name of the supplier
     * @param description the description of the supplier
     * @param email       the email address of the supplier
     * @param phone       the phone number of the supplier
     * @param address     the address of the supplier
     */
    public Supplier(int id, String name, String description, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.address = address;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name;
    }
}
