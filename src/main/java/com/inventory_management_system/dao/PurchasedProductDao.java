package com.inventory_management_system.dao;

import com.inventory_management_system.model.PurchasedProduct;

import java.util.List;

public interface PurchasedProductDao {
    List<PurchasedProduct> getAllPurchasedProducts();
    PurchasedProduct getPurchasedProductById(int id);
    void addPurchasedProduct(PurchasedProduct purchasedProduct);
    void updatePurchasedProduct(PurchasedProduct purchasedProduct);
    void deletePurchasedProduct(int id);
}
