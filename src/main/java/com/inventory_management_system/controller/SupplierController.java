package com.inventory_management_system.controller;

import com.inventory_management_system.dao.SupplierDao;
import com.inventory_management_system.dao.impl.SupplierDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
import com.inventory_management_system.model.Supplier;
import com.inventory_management_system.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * The SupplierController class handles operations related to supplier management.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class SupplierController {
    private final SupplierDao supplierDao;

    /**
     * Constructs a SupplierController object and initializes the SupplierDao.
     */
    public SupplierController() {
        supplierDao = new SupplierDaoImpl();
    }

    /**
     * Retrieves all suppliers.
     *
     * @return A list of all suppliers.
     */
    public List<Supplier> getAllSuppliers() {
        return supplierDao.getAllSuppliers();
    }

    /**
     * Retrieves filtered suppliers based on provided filters.
     *
     * @param filterName    The name filter.
     * @param filterEmail   The email filter.
     * @param filterPhone   The phone filter.
     * @param filterAddress The address filter.
     * @return A list of filtered suppliers.
     */
    public List<Supplier> getFilteredSuppliers(String filterName, String filterEmail, String filterPhone, String filterAddress) {
        return supplierDao.getFilteredSuppliers(filterName, filterEmail, filterPhone, filterAddress);
    }

    /**
     * Retrieves a supplier by its ID.
     *
     * @param id The ID of the supplier to retrieve.
     * @return The supplier with the specified ID.
     */
    public Supplier getSupplierById(int id) {
        return supplierDao.getSupplierById(id);
    }

    /**
     * Retrieves a supplier by its name.
     *
     * @param name The name of the supplier to retrieve.
     * @return The supplier with the specified name.
     */
    public Supplier getSupplierByName(String name) {
        return supplierDao.getSupplierByName(name);
    }

    /**
     * Adds a new supplier.
     *
     * @param supplier The supplier to add.
     * @throws AlreadyExistsException If a supplier with the same details already exists.
     */
    public void addSupplier(Supplier supplier) {
        if (!supplierDao.doesSupplierExist(supplier)) {
            supplierDao.addSupplier(supplier);
        } else {
            new AlreadyExistsException("Supplier");
        }
    }

    /**
     * Updates an existing supplier.
     *
     * @param supplier The supplier to update.
     */
    public void updateSupplier(Supplier supplier) {
        supplierDao.updateSupplier(supplier);
    }

    /**
     * Updates multiple suppliers.
     *
     * @param suppliers A list of suppliers to update.
     * @throws AlreadyExistsException If a supplier with the same details already exists.
     */
    public void updateSuppliers(List<Supplier> suppliers) {
        Set<String> nameSet = new HashSet<>();
        for (Supplier supplier : suppliers){
            if (supplierDao.doesSupplierExist(supplier)) {
                new AlreadyExistsException("Supplier");
                return;
            } else if (!nameSet.add(supplier.getName())) {
                new AlreadyExistsException("Supplier");
                return;
            }
        }
        supplierDao.updateSuppliers(suppliers);
    }

    /**
     * Deletes a supplier by its ID.
     *
     * @param id The ID of the supplier to delete.
     */
    public void deleteSupplier(int id) {
        supplierDao.deleteSupplier(id);
    }
}
