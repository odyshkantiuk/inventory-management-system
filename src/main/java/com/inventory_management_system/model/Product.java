package com.inventory_management_system.model;
/**
 * Product model class.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private double purchasePrice;
    private double salePrice;
    private int quantity;
    private Category category;
    private Supplier supplier;

    /**
     * Constructs a Product object with the specified id, name, description, purchase price, sale price, quantity,
     * category, and supplier.
     *
     * @param id            the unique identifier for the product
     * @param name          the name of the product
     * @param description   the description of the product
     * @param purchasePrice the purchase price of the product
     * @param salePrice     the sale price of the product
     * @param quantity      the quantity of the product
     * @param category      the category of the product
     * @param supplier      the supplier of the product
     */
    public Product(int id, String name, String description, double purchasePrice, double salePrice, int quantity, Category category, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.salePrice = salePrice;
        this.quantity = quantity;
        this.category = category;
        this.supplier = supplier;
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

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return name;
    }
}
