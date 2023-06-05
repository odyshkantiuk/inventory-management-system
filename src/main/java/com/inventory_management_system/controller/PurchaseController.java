package com.inventory_management_system.controller;

import com.inventory_management_system.dao.PurchaseDao;
import com.inventory_management_system.dao.impl.PurchaseDaoImpl;
import com.inventory_management_system.exception.QuantityException;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Purchase;

import java.util.Date;
import java.util.List;
/**
 * The PurchaseController class handles operations related to purchase management.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class PurchaseController {
    private final PurchaseDao purchaseDao;
    private final ProductController productController;

    /**
     * Constructs a PurchaseController object and initializes the PurchaseDao and ProductController.
     */
    public PurchaseController() {
        purchaseDao = new PurchaseDaoImpl();
        productController = new ProductController();
    }

    /**
     * Retrieves all purchases.
     *
     * @return A list of all purchases.
     */
    public List<Purchase> getAllPurchases() {
        return purchaseDao.getAllPurchases();
    }

    /**
     * Retrieves filtered purchases based on provided filters.
     *
     * @param filterProduct The product filter.
     * @param fromDate      The start date filter.
     * @param toDate        The end date filter.
     * @return A list of filtered purchases.
     */
    public List<Purchase> getFilteredPurchases(int filterProduct, Date fromDate, Date toDate) {
        return purchaseDao.getFilteredPurchases(filterProduct, fromDate, toDate);
    }

    /**
     * Retrieves a purchase by its ID.
     *
     * @param id The ID of the purchase to retrieve.
     * @return The purchase with the specified ID.
     */
    public Purchase getPurchaseById(int id) {
        return purchaseDao.getPurchaseById(id);
    }

    /**
     * Adds a new purchase.
     *
     * @param purchase The purchase to add.
     * @throws QuantityException If the quantity of the purchase is not greater than zero.
     */
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

    /**
     * Updates an existing purchase.
     *
     * @param purchase The purchase to update.
     */
    public void updatePurchase(Purchase purchase) {
        purchaseDao.updatePurchase(purchase);
    }

    /**
     * Updates multiple purchases.
     *
     * @param purchases A list of purchases to update.
     * @throws QuantityException If the quantity of any purchase is not greater than zero.
     */
    public void updatePurchases(List<Purchase> purchases) {
        for (Purchase purchase : purchases) {
            if (purchase.getQuantity() > 0) {
                Purchase oldPurchase = purchaseDao.getPurchaseById(purchase.getId());
                Product product = purchase.getProduct();
                int quantity = purchase.getQuantity() - oldPurchase.getQuantity();
                if (product.getQuantity() + quantity >= 0) {
                    product.setQuantity(product.getQuantity() + quantity);
                } else {
                    product.setQuantity(0);
                }
                productController.updateProduct(product);
            } else {
                new QuantityException();
                return;
            }
        }
        purchaseDao.updatePurchases(purchases);
    }

    /**
     * Deletes a purchase by its ID.
     *
     * @param id The ID of the purchase to delete.
     */
    public void deletePurchase(int id) {
        Purchase purchase = purchaseDao.getPurchaseById(id);
        Product product = purchase.getProduct();
        int purchaseQuantity = purchase.getQuantity();
        int productQuantity = product.getQuantity();
        if (productQuantity - purchaseQuantity >= 0) {
            product.setQuantity(productQuantity - purchaseQuantity);
        } else {
            product.setQuantity(0);
        }
        productController.updateProduct(product);
        purchaseDao.deletePurchase(id);
    }
}
