package com.inventory_management_system.controller;

import com.inventory_management_system.dao.SaleDao;
import com.inventory_management_system.dao.impl.SaleDaoImpl;
import com.inventory_management_system.exception.NotEnoughException;
import com.inventory_management_system.exception.QuantityException;
import com.inventory_management_system.model.Product;
import com.inventory_management_system.model.Sale;

import java.util.Date;
import java.util.List;
/**
 * The SaleController class handles operations related to sale management.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class SaleController {
    private final SaleDao saleDao;
    private final ProductController productController;

    /**
     * Constructs a SaleController object and initializes the SaleDao and ProductController.
     */
    public SaleController() {
        saleDao = new SaleDaoImpl();
        productController = new ProductController();
    }

    /**
     * Retrieves all sales.
     *
     * @return A list of all sales.
     */
    public List<Sale> getAllSales() {
        return saleDao.getAllSales();
    }

    /**
     * Retrieves filtered sales based on provided filters.
     *
     * @param filterProduct  The product filter.
     * @param filterCustomer The customer filter.
     * @param fromDate       The start date filter.
     * @param toDate         The end date filter.
     * @return A list of filtered sales.
     */
    public List<Sale> getFilteredSales(int filterProduct, int filterCustomer, Date fromDate, Date toDate) {
        return saleDao.getFilteredSales(filterProduct, filterCustomer, fromDate, toDate);
    }

    /**
     * Retrieves a sale by its ID.
     *
     * @param id The ID of the sale to retrieve.
     * @return The sale with the specified ID.
     */
    public Sale getSaleById(int id) {
        return saleDao.getSaleById(id);
    }

    /**
     * Adds a new sale.
     *
     * @param sale The sale to add.
     * @throws NotEnoughException If there is not enough quantity of the product available for sale.
     */
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

    /**
     * Updates an existing sale.
     *
     * @param sale The sale to update.
     */
    public void updateSale(Sale sale) {
        saleDao.updateSale(sale);
    }

    /**
     * Updates multiple sales.
     *
     * @param sales A list of sales to update.
     * @throws QuantityException   If the quantity of any sale is not greater than zero.
     * @throws NotEnoughException  If there is not enough quantity of a product available for sale.
     */
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

    /**
     * Deletes a sale by its ID.
     *
     * @param id The ID of the sale to delete.
     */
    public void deleteSale(int id) {
        Sale sale = saleDao.getSaleById(id);
        Product product = sale.getProduct();
        product.setQuantity(product.getQuantity() + sale.getQuantity());
        productController.updateProduct(product);
        saleDao.deleteSale(id);
    }
}
