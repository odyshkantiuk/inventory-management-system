package com.inventory_management_system.controller;

import com.inventory_management_system.dao.SupplierDao;
import com.inventory_management_system.dao.impl.SupplierDaoImpl;
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

    public Supplier getSupplierById(int id) {
        return supplierDao.getSupplierById(id);
    }

    public void addSupplier(Supplier supplier) {
        supplierDao.addSupplier(supplier);
    }

    public void updateSupplier(Supplier supplier) {
        supplierDao.updateSupplier(supplier);
    }

    public void deleteSupplier(int id) {
        supplierDao.deleteSupplier(id);
    }
}
