package com.inventory_management_system.controller;

import com.inventory_management_system.dao.ProductDao;
import com.inventory_management_system.dao.impl.ProductDaoImpl;
import com.inventory_management_system.model.Product;

import java.util.List;

public class ProductController {
    private final ProductDao productDao;

    public ProductController() {
        productDao = new ProductDaoImpl();
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public Product getProductById(int id) {
        return productDao.getProductById(id);
    }

    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productDao.deleteProduct(id);
    }
}