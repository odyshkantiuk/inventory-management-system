package com.inventory_management_system.model;

import java.sql.Timestamp;

public class Sale {
    private int id;
    private Timestamp time;
    private Customer customer;
    private Product product;
    private int quantity;
    private double total;

    public Sale(int id, Timestamp time, Customer customer, Product product, int quantity) {
        this.id = id;
        this.time = time;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double calculateTotal() {
        total = product.getSalePrice() * quantity;
        return total;
    }
}