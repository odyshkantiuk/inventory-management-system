package com.inventory_management_system.dao;

import com.inventory_management_system.model.Sale;

import java.util.Date;
import java.util.List;
/**
 * Sale dao interface.
 *
 * @author Oleksandr Dyshkantiuk
 */
public interface SaleDao {
    /**
     * Retrieves all sales from the data source.
     *
     * @return a list of all sales
     */
    List<Sale> getAllSales();

    /**
     * Retrieves filtered sales from the data source based on the provided filters.
     *
     * @param filterProduct  the product filter
     * @param filterCustomer the customer filter
     * @param fromDate       the start date filter
     * @param toDate         the end date filter
     * @return a list of filtered sales
     */
    List<Sale> getFilteredSales(int filterProduct, int filterCustomer, Date fromDate, Date toDate);

    /**
     * Retrieves a sale from the data source based on its id.
     *
     * @param id the id of the sale to retrieve
     * @return the sale with the specified id, or null if not found
     */
    Sale getSaleById(int id);

    /**
     * Adds a new sale to the data source.
     *
     * @param sale the sale to add
     */
    void addSale(Sale sale);

    /**
     * Updates an existing sale in the data source.
     *
     * @param sale the sale to update
     */
    void updateSale(Sale sale);

    /**
     * Updates multiple sales in the data source.
     *
     * @param sales the list of sales to update
     */
    void updateSales(List<Sale> sales);

    /**
     * Deletes a sale from the data source based on its id.
     *
     * @param id the id of the sale to delete
     */
    void deleteSale(int id);
}
