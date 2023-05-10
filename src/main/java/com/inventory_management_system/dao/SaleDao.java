package com.inventory_management_system.dao;

import com.inventory_management_system.model.Sale;

import java.util.List;

public interface SaleDao {
    List<Sale> getAllSales();
    Sale getSaleById(int id);
    void addSale(Sale sale);
    void updateSale(Sale sale);
    void deleteSale(int id);
}
