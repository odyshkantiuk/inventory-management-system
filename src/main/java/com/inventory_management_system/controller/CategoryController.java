package com.inventory_management_system.controller;

import com.inventory_management_system.dao.CategoryDao;
import com.inventory_management_system.dao.impl.CategoryDaoImpl;
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

    public void addCategory(Category category) {
        categoryDao.addCategory(category);
    }

    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    public void deleteCategory(int id) {
        categoryDao.deleteCategory(id);
    }
}
