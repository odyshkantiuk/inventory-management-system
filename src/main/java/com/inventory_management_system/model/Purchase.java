package com.inventory_management_system.model;

import java.sql.Timestamp;

public class Purchase {
    private int id;
    private Timestamp time;
    private Product product;
    private int quantity;
    private double total;

    public Purchase(int id, Timestamp time, Product product, int quantity) {
        this.id = id;
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
        total = product.getPurchasePrice() * quantity;
        return total;
    }
}
