package com.inventory_management_system.dao;

import com.inventory_management_system.model.Purchase;

import java.util.Date;
import java.util.List;
/**
 * Purchase dao interface.
 *
 * @author Oleksandr Dyshkantiuk
 */
public interface PurchaseDao {
    /**
     * Retrieves all purchases from the data source.
     *
     * @return a list of all purchases
     */
    List<Purchase> getAllPurchases();

    /**
     * Retrieves filtered purchases from the data source based on the provided filters.
     *
     * @param filterProduct the product filter
     * @param fromDate      the start date filter
     * @param toDate        the end date filter
     * @return a list of filtered purchases
     */
    List<Purchase> getFilteredPurchases(int filterProduct, Date fromDate, Date toDate);

    /**
     * Retrieves a purchase from the data source based on its id.
     *
     * @param id the id of the purchase to retrieve
     * @return the purchase with the specified id, or null if not found
     */
    Purchase getPurchaseById(int id);

    /**
     * Adds a new purchase to the data source.
     *
     * @param purchase the purchase to add
     */
    void addPurchase(Purchase purchase);

    /**
     * Updates an existing purchase in the data source.
     *
     * @param purchase the purchase to update
     */
    void updatePurchase(Purchase purchase);

    /**
     * Updates multiple purchases in the data source.
     *
     * @param purchases the list of purchases to update
     */
    void updatePurchases(List<Purchase> purchases);

    /**
     * Deletes a purchase from the data source based on its id.
     *
     * @param id the id of the purchase to delete
     */
    void deletePurchase(int id);
}
