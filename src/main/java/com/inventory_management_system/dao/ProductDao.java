package com.inventory_management_system.dao;

import com.inventory_management_system.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();
    List<Product> getFilteredProducts(String filterName, int filterCategory, int filterSupplier);
    Product getProductById(int id);
    Product getProductByName(String name);
    boolean addProduct(Product product);
    void updateProduct(Product product);
    void updateProducts(List<Product> products);
    void deleteProduct(int id);
    boolean doesProductExist(Product product);
}
