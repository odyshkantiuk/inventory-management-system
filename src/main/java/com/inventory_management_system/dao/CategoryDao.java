package com.inventory_management_system.dao;

import com.inventory_management_system.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    Category getCategoryByName(String name);
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(String name);
    boolean doesCategoryExist(Category category);
}