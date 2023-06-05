package com.inventory_management_system.controller;

import com.inventory_management_system.dao.SaleDao;
import com.inventory_management_system.dao.impl.SaleDaoImpl;
import com.inventory_management_system.exception.NotEnoughException;
import com.inventory_management_system.exception.QuantityException;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Sale;

import java.util.Date;
import java.util.List;

public class SaleController {
    private final SaleDao saleDao;
    private final ProductController productController;

    public SaleController() {
        saleDao = new SaleDaoImpl();
        productController = new ProductController();
    }

    public List<Sale> getAllSales() {
        return saleDao.getAllSales();
    }

    public List<Sale> getFilteredSales(int filterProduct, int filterCustomer, Date fromDate, Date toDate) {
        return saleDao.getFilteredSales(filterProduct, filterCustomer, fromDate, toDate);
    }

    public Sale getSaleById(int id) {
        return saleDao.getSaleById(id);
    }

    public void addSale(Sale sale) {
        Product product = sale.getProduct();
        if (sale.getQuantity() > 0 && product.getQuantity() - sale.getQuantity() >= 0) {
            saleDao.addSale(sale);
            product.setQuantity(product.getQuantity() - sale.getQuantity());
            productController.updateProduct(product);
        } else {
            new NotEnoughException();
        }
    }

    public void updateSale(Sale sale) {
        saleDao.updateSale(sale);
    }

    public void updateSales(List<Sale> sales) {
        for (Sale sale : sales) {
            if (sale.getQuantity() > 0) {
                Sale oldSale = saleDao.getSaleById(sale.getId());
                Product product = sale.getProduct();
                int quantity = oldSale.getQuantity() - sale.getQuantity();
                if (product.getQuantity() + quantity >= 0) {
                    product.setQuantity(product.getQuantity() + quantity);
                    productController.updateProduct(product);
                } else {
                    new NotEnoughException();
                    return;
                }
            } else {
                new QuantityException();
                return;
            }
        }
        saleDao.updateSales(sales);
    }

    public void deleteSale(int id) {
        Sale sale = saleDao.getSaleById(id);
        Product product = sale.getProduct();
        product.setQuantity(product.getQuantity() + sale.getQuantity());
        productController.updateProduct(product);
        saleDao.deleteSale(id);
    }
}
