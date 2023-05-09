package com.inventory_management_system.dao;

import com.inventory_management_system.model.Supplier;

import java.util.List;

public interface SupplierDao {
    List<Supplier> getAllSuppliers();
    Supplier getSupplierById(int id);
    void addSupplier(Supplier supplier);
    void updateSupplier(Supplier supplier);
    void deleteSupplier(int id);
}
