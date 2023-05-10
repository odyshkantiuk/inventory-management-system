package com.inventory_management_system.dao;

import com.inventory_management_system.model.Purchase;

import java.util.List;

public interface PurchaseDao {
    List<Purchase> getAllPurchases();
    Purchase getPurchaseById(int id);
    void addPurchase(Purchase purchase);
    void updatePurchase(Purchase purchase);
    void deletePurchase(int id);
}
