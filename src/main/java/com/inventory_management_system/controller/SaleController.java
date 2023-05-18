package com.inventory_management_system.controller;

import com.inventory_management_system.dao.SaleDao;
import com.inventory_management_system.dao.impl.SaleDaoImpl;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Sale;

import java.util.List;

public class SaleController {
    private final SaleDao saleDao;

    public SaleController() {
        saleDao = new SaleDaoImpl();
    }

    public List<Sale> getAllSales() {
        return saleDao.getAllSales();
    }

    public Sale getSaleById(int id) {
        return saleDao.getSaleById(id);
    }

    public void addSale(Sale sale) {
        saleDao.addSale(sale);
        Product product = sale.getProduct();
        product.setQuantity(product.getQuantity() + sale.getQuantity());
    }

    public void updateSale(Sale sale) {
        saleDao.updateSale(sale);
        Product product = sale.getProduct();
        product.setQuantity(product.getQuantity() + sale.getQuantity());
    }

    public void deleteSale(int id) {
        saleDao.deleteSale(id);
    }
}
