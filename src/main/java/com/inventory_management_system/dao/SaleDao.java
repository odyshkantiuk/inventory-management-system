package com.inventory_management_system.dao;

import com.inventory_management_system.model.Sale;

import java.util.Date;
import java.util.List;

public interface SaleDao {
    List<Sale> getAllSales();
    List<Sale> getFilteredSales(int filterProduct, int filterCustomer, Date fromDate, Date toDate);
    Sale getSaleById(int id);
    void addSale(Sale sale);
    void updateSale(Sale sale);
    void updateSales(List<Sale> sale);
    void deleteSale(int id);
}
