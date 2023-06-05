package com.inventory_management_system.controller;

import com.inventory_management_system.dao.ProductDao;
import com.inventory_management_system.dao.impl.ProductDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
import com.inventory_management_system.exception.QuantityException;
import com.inventory_management_system.model.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * The ProductController class handles operations related to product management.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class ProductController {
    private final ProductDao productDao;

    /**
     * Constructs a ProductController object and initializes the ProductDao.
     */
    public ProductController() {
        productDao = new ProductDaoImpl();
    }

    /**
     * Retrieves all products.
     *
     * @return A list of all products.
     */
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    /**
     * Retrieves filtered products based on provided filters.
     *
     * @param filterName     The name filter.
     * @param filterCategory The category filter.
     * @param filterSupplier The supplier filter.
     * @return A list of filtered products.
     */
    public List<Product> getFilteredProducts(String filterName, int filterCategory, int filterSupplier) {
        return productDao.getFilteredProducts(filterName, filterCategory, filterSupplier);
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product to retrieve.
     * @return The product with the specified ID.
     */
    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }

    /**
     * Retrieves a product by its name.
     *
     * @param name The name of the product to retrieve.
     * @return The product with the specified name.
     */
    public Product getProductByName(String name) {
        return productDao.getProductByName(name);
    }

    /**
     * Adds a new product.
     *
     * @param product The product to add.
     * @return true if the product was added successfully, false otherwise.
     * @throws AlreadyExistsException If the product already exists.
     * @throws QuantityException      If the quantity of the product is not greater than zero.
     */
    public boolean addProduct(Product product) {
        if (product.getQuantity() > 0) {
            if (!productDao.doesProductExist(product)) {
                return productDao.addProduct(product);
            } else {
                new AlreadyExistsException("Product");
                return false;
            }
        } else {
            new QuantityException();
            return false;
        }
    }

    /**
     * Updates an existing product.
     *
     * @param product The product to update.
     */
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    /**
     * Updates multiple products.
     *
     * @param products A list of products to update.
     * @throws AlreadyExistsException If any product already exists or if any product name is a duplicate.
     * @throws QuantityException      If the quantity of any product is not greater than zero.
     */
    public void updateProducts(List<Product> products) {
        Set<String> nameSet = new HashSet<>();
        for (Product product : products) {
            if (product.getQuantity() > 0) {
                if (productDao.doesProductExist(product)) {
                    new AlreadyExistsException("Product");
                    return;
                } else if (!nameSet.add(product.getName())) {
                    new AlreadyExistsException("Product");
                    return;
                }
            } else {
                new QuantityException();
                return;
            }
        }
        productDao.updateProducts(products);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     */
    public void deleteProduct(int id) {
        productDao.deleteProduct(id);
    }
}