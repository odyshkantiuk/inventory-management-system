package com.inventory_management_system.dao;

import com.inventory_management_system.model.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product getProductByName(String name);
    void addProduct(Product product);
    void updateProduct(Product product);
    void updateProducts(List<Product> products);
    void deleteProduct(int id);
    boolean doesProductExist(Product product);
}
