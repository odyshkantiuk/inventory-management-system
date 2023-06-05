package com.inventory_management_system.dao;

import com.inventory_management_system.model.Supplier;

import java.util.List;
/**
 * Supplier dao interface.
 *
 * @author Oleksandr Dyshkantiuk
 */
public interface SupplierDao {
    /**
     * Retrieves all suppliers from the data source.
     *
     * @return a list of all suppliers
     */
    List<Supplier> getAllSuppliers();

    /**
     * Retrieves filtered suppliers from the data source based on the provided filters.
     *
     * @param filterName    the name filter
     * @param filterEmail   the email filter
     * @param filterPhone   the phone filter
     * @param filterAddress the address filter
     * @return a list of filtered suppliers
     */
    List<Supplier> getFilteredSuppliers(String filterName, String filterEmail, String filterPhone, String filterAddress);

    /**
     * Retrieves a supplier from the data source based on its id.
     *
     * @param id the id of the supplier to retrieve
     * @return the supplier with the specified id, or null if not found
     */
    Supplier getSupplierById(int id);

    /**
     * Retrieves a supplier from the data source based on its name.
     *
     * @param name the name of the supplier to retrieve
     * @return the supplier with the specified name, or null if not found
     */
    Supplier getSupplierByName(String name);

    /**
     * Adds a new supplier to the data source.
     *
     * @param supplier the supplier to add
     */
    void addSupplier(Supplier supplier);

    /**
     * Updates an existing supplier in the data source.
     *
     * @param supplier the supplier to update
     */
    void updateSupplier(Supplier supplier);

    /**
     * Updates multiple suppliers in the data source.
     *
     * @param suppliers the list of suppliers to update
     */
    void updateSuppliers(List<Supplier> suppliers);

    /**
     * Deletes a supplier from the data source based on its id.
     *
     * @param id the id of the supplier to delete
     */
    void deleteSupplier(int id);

    /**
     * Checks if a supplier exists in the data source.
     *
     * @param supplier the supplier to check
     * @return true if the supplier exists, false otherwise
     */
    boolean doesSupplierExist(Supplier supplier);
}
