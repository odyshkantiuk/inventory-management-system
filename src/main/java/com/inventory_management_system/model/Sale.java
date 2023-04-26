package com.inventory_management_system.model;

import java.util.Date;
import java.util.List;

public class Sale {
    private int id;
    private Date date;
    private List<SoldProduct> soldProducts;
    private double total;
    private Customer customer;

    public Sale(int id, double price, Date date, List<SoldProduct> soldProducts, Customer customer) {
        this.id = id;
        this.date = date;
        this.soldProducts = soldProducts;
        this.customer = customer;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<SoldProduct> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProduct> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public void addSoldProduct(SoldProduct soldProduct) {
        this.soldProducts.add(soldProduct);
    }

    public void removeSoldProduct(SoldProduct soldProduct) {
        this.soldProducts.remove(soldProduct);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double calculateTotal() {
        for (SoldProduct soldProduct : soldProducts) {
            total += soldProduct.calculateTotal();
        }
        return total;
    }
}