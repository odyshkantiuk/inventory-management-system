package com.inventory_management_system.model;

public class SoldProduct {
    private int id;
    private Product product;
    private int quantity;
    private double total;

    public SoldProduct(int id, Product product, int quantity) {
        this.id = id;
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
