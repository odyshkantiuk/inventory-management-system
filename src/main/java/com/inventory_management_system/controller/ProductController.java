package com.inventory_management_system.controller;

import com.inventory_management_system.dao.ProductDao;
import com.inventory_management_system.dao.impl.ProductDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
import com.inventory_management_system.model.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public void updateProducts(List<Product> products) {
        Set<String> nameSet = new HashSet<>();
        for (Product product : products) {
            if (productDao.doesProductExist(product)) {
                new AlreadyExistsException("Product");
                return;
            } else if (!nameSet.add(product.getName())) {
                new AlreadyExistsException("Product");
                return;
            }
        }
        productDao.updateProducts(products);
    }

    public void deleteProduct(int id) {
        productDao.deleteProduct(id);
    }
}