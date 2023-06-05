package com.inventory_management_system.model;
/**
 * Customer model class.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;

    /**
     * Constructs a Customer object with the specified id, name, email, phone, and address.
     *
     * @param id      the unique identifier for the customer
     * @param name    the name of the customer
     * @param email   the email address of the customer
     * @param phone   the phone number of the customer
     * @param address the address of the customer
     */
    public Customer(int id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

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
