package com.inventory_management_system.model;

import java.util.Date;
import java.util.List;

public class Purchase {
    private int id;
    private Date date;
    private List<PurchasedProduct> purchasedProducts;
    private double total;

    public Purchase(int id, Date date, List<PurchasedProduct> purchasedProducts) {
        this.id = id;
        this.date = date;
        this.purchasedProducts = purchasedProducts;
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

    public List<PurchasedProduct> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<PurchasedProduct> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public void addPurchasedProduct(PurchasedProduct purchasedProduct) {
        this.purchasedProducts.add(purchasedProduct);
    }

    public void removePurchasedProduct(PurchasedProduct purchasedProduct) {
        this.purchasedProducts.remove(purchasedProduct);
    }

    public double calculateTotal() {
        for (PurchasedProduct purchasedProduct : purchasedProducts) {
            total += purchasedProduct.calculateTotal();
        }
        return total;
    }
}