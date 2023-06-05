package com.inventory_management_system.controller;

import com.inventory_management_system.dao.PurchaseDao;
import com.inventory_management_system.dao.impl.PurchaseDaoImpl;
import com.inventory_management_system.exception.QuantityException;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Purchase;

import java.util.List;

public class PurchaseController {
    private final PurchaseDao purchaseDao;
    private final ProductController productController;

    public PurchaseController() {
        purchaseDao = new PurchaseDaoImpl();
        productController = new ProductController();
    }

    public List<Purchase> getAllPurchases() {
        return purchaseDao.getAllPurchases();
    }

    public Purchase getPurchaseById(int id) {
        return purchaseDao.getPurchaseById(id);
    }

    public void addPurchase(Purchase purchase) {
        if (purchase.getQuantity() > 0) {
            purchaseDao.addPurchase(purchase);
            Product product = purchase.getProduct();
            product.setQuantity(product.getQuantity() + purchase.getQuantity());
            product.setPurchasePrice(purchase.getPrice());
            productController.updateProduct(product);
        } else {
            new QuantityException();
        }
    }

    public void updatePurchase(Purchase purchase) {
        purchaseDao.updatePurchase(purchase);
    }

    public void deletePurchase(int id) {
        purchaseDao.deletePurchase(id);
    }
}
