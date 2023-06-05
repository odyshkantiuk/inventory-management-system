package com.inventory_management_system.dao;

import com.inventory_management_system.model.Product;

import java.util.List;
/**
 * Product dao interface.
 *
 * @author Oleksandr Dyshkantiuk
 */
public interface ProductDao {
    /**
     * Retrieves all products from the data source.
     *
     * @return a list of all products
     */
    List<Product> getAllProducts();

    /**
     * Retrieves filtered products from the data source based on the provided filters.
     *
     * @param filterName     the name filter
     * @param filterCategory the category filter
     * @param filterSupplier the supplier filter
     * @return a list of filtered products
     */
    List<Product> getFilteredProducts(String filterName, int filterCategory, int filterSupplier);

    /**
     * Retrieves a product from the data source based on its id.
     *
     * @param id the id of the product to retrieve
     * @return the product with the specified id, or null if not found
     */
    Product getProductById(int id);

    /**
     * Retrieves a product from the data source based on its name.
     *
     * @param name the name of the product to retrieve
     * @return the product with the specified name, or null if not found
     */
    Product getProductByName(String name);

    /**
     * Adds a new product to the data source.
     *
     * @param product the product to add
     * @return true if the product was added successfully, false otherwise
     */
    boolean addProduct(Product product);

    /**
     * Updates an existing product in the data source.
     *
     * @param product the product to update
     */
    void updateProduct(Product product);

    /**
     * Updates multiple products in the data source.
     *
     * @param products the list of products to update
     */
    void updateProducts(List<Product> products);

    /**
     * Deletes a product from the data source based on its id.
     *
     * @param id the id of the product to delete
     */
    void deleteProduct(int id);

    /**
     * Checks if a product already exists in the data source.
     *
     * @param product the product to check
     * @return true if the product exists, false otherwise
     */
    boolean doesProductExist(Product product);
}
