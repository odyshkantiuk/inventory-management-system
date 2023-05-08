package com.inventory_management_system.dao;

import java.util.List;
import com.inventory_management_system.model.Category;

public interface CategoryDao {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    void addCategory(Category category);
    void updateCategory(Category category);
    void deleteCategory(int id);
}