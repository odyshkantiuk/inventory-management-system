package com.inventory_management_system.controller;

import com.inventory_management_system.dao.ProductDao;
import com.inventory_management_system.dao.impl.ProductDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
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

    public Product getProductByName(String name) {
        return productDao.getProductByName(name);
    }

    public boolean addProduct(Product product) {
        if (!productDao.doesProductExist(product)) {
            productDao.addProduct(product);
            return true;
        } else {
            new AlreadyExistsException("Product");
            return false;
        }
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productDao.deleteProduct(id);
    }
}