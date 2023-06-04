package com.inventory_management_system.model;

import java.sql.Timestamp;

public class Purchase {
    private int id;
    private double price;
    private Timestamp time;
    private Product product;
    private int quantity;
    private double total;

    public Purchase(int id, double price, Timestamp time, Product product, int quantity) {
        this.id = id;
        this.price = price;
        this.time = time;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
        total = price * quantity;
        return total;
    }
}
