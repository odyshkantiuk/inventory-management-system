package com.inventory_management_system.controller;

import com.inventory_management_system.dao.CategoryDao;
import com.inventory_management_system.dao.impl.CategoryDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
import com.inventory_management_system.model.Category;

import java.util.List;

public class CategoryController {
    private final CategoryDao categoryDao;

    public CategoryController() {
        categoryDao = new CategoryDaoImpl();
    }

    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    public Category getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }

    public Category getCategoryByName(String name) {
        return categoryDao.getCategoryByName(name);
    }

    public void addCategory(Category category) {
        if (!categoryDao.doesCategoryExist(category)) {
            categoryDao.addCategory(category);
        } else {
            new AlreadyExistsException("Category");
        }
    }

    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    public void deleteCategory(String name) {
        categoryDao.deleteCategory(name);
    }
}
