package com.inventory_management_system.controller;

import com.inventory_management_system.dao.CategoryDao;
import com.inventory_management_system.dao.impl.CategoryDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
import com.inventory_management_system.model.Category;

import java.util.List;
/**
 * The CategoryController class handles operations related to category management.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class CategoryController {
    private final CategoryDao categoryDao;

    /**
     * Constructs a CategoryController object and initializes the CategoryDao.
     */
    public CategoryController() {
        categoryDao = new CategoryDaoImpl();
    }

    /**
     * Retrieves all categories.
     *
     * @return A list of all categories.
     */
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return The category with the specified ID.
     */
    public Category getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name The name of the category to retrieve.
     * @return The category with the specified name.
     */
    public Category getCategoryByName(String name) {
        return categoryDao.getCategoryByName(name);
    }

    /**
     * Adds a new category.
     *
     * @param category The category to add.
     * @throws AlreadyExistsException If the category already exists.
     */
    public void addCategory(Category category) {
        if (!categoryDao.doesCategoryExist(category)) {
            categoryDao.addCategory(category);
        } else {
            new AlreadyExistsException("Category");
        }
    }

    /**
     * Updates an existing category.
     *
     * @param category The category to update.
     */
    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    /**
     * Deletes a category by its name.
     *
     * @param name The name of the category to delete.
     */
    public void deleteCategory(String name) {
        categoryDao.deleteCategory(name);
    }
}
