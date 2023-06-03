package com.inventory_management_system.controller;

import com.inventory_management_system.dao.SupplierDao;
import com.inventory_management_system.dao.impl.SupplierDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
import com.inventory_management_system.model.Supplier;

import java.util.List;

public class SupplierController {
    private final SupplierDao supplierDao;

    public SupplierController() {
        supplierDao = new SupplierDaoImpl();
    }

    public List<Supplier> getAllSuppliers() {
        return supplierDao.getAllSuppliers();
    }

    public List<Supplier> getFilteredSuppliers(String filterName, String filterEmail, String filterPhone, String filterAddress) {
        return supplierDao.getFilteredSuppliers(filterName, filterEmail, filterPhone, filterAddress);
    }

    public Supplier getSupplierById(int id) {
        return supplierDao.getSupplierById(id);
    }

    public void addSupplier(Supplier supplier) {
        if (!supplierDao.doesSupplierExist(supplier.getName())) {
            supplierDao.addSupplier(supplier);
        } else {
            new AlreadyExistsException("Supplier");
        }
    }

    public void updateSupplier(Supplier supplier) {
        supplierDao.updateSupplier(supplier);
    }

    public void deleteSupplier(int id) {
        supplierDao.deleteSupplier(id);
    }
}
