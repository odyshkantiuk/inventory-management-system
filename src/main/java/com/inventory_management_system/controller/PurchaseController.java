package com.inventory_management_system.controller;

import com.inventory_management_system.dao.PurchaseDao;
import com.inventory_management_system.dao.impl.PurchaseDaoImpl;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Purchase;

import java.util.List;

public class PurchaseController {
    private final PurchaseDao purchaseDao;

    public PurchaseController() {
        purchaseDao = new PurchaseDaoImpl();
    }

    public List<Purchase> getAllPurchases() {
        return purchaseDao.getAllPurchases();
    }

    public Purchase getPurchaseById(int id) {
        return purchaseDao.getPurchaseById(id);
    }

    public void addPurchase(Purchase purchase) {
        purchaseDao.addPurchase(purchase);
    }

    public void updatePurchase(Purchase purchase) {
        purchaseDao.updatePurchase(purchase);
    }

    public void deletePurchase(int id) {
        purchaseDao.deletePurchase(id);
    }
}
