package com.inventory_management_system.dao;

import com.inventory_management_system.model.Supplier;

import java.util.List;

public interface SupplierDao {
    List<Supplier> getAllSuppliers();
    List<Supplier> getFilteredSuppliers(String filterName, String filterEmail, String filterPhone, String filterAddress);
    Supplier getSupplierById(int id);
    Supplier getSupplierByName(String name);
    void addSupplier(Supplier supplier);
    void updateSupplier(Supplier supplier);
    void updateSuppliers(List<Supplier> suppliers);
    void deleteSupplier(int id);
    boolean doesSupplierExist(Supplier supplier);
}
