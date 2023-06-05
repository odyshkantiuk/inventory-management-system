package com.inventory_management_system.dao;

import com.inventory_management_system.model.Purchase;

import java.util.Date;
import java.util.List;

public interface PurchaseDao {
    List<Purchase> getAllPurchases();
    List<Purchase> getFilteredPurchases(int filterProduct, Date fromDate, Date toDate);
    Purchase getPurchaseById(int id);
    void addPurchase(Purchase purchase);
    void updatePurchase(Purchase purchase);
    void updatePurchases(List<Purchase> purchases);
    void deletePurchase(int id);
}
