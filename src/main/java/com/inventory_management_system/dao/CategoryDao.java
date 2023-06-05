package com.inventory_management_system.dao;

import com.inventory_management_system.model.Category;

import java.util.List;
/**
 * Category dao interface.
 *
 * @author Oleksandr Dyshkantiuk
 */
public interface CategoryDao {
    /**
     * Retrieves all categories from the data source.
     *
     * @return a list of all categories
     */
    List<Category> getAllCategories();

    /**
     * Retrieves a category from the data source based on its id.
     *
     * @param id the id of the category to retrieve
     * @return the category with the specified id, or null if not found
     */
    Category getCategoryById(int id);

    /**
     * Retrieves a category from the data source based on its name.
     *
     * @param name the name of the category to retrieve
     * @return the category with the specified name, or null if not found
     */
    Category getCategoryByName(String name);

    /**
     * Adds a new category to the data source.
     *
     * @param category the category to add
     */
    void addCategory(Category category);

    /**
     * Updates an existing category in the data source.
     *
     * @param category the category to update
     */
    void updateCategory(Category category);

    /**
     * Deletes a category from the data source based on its name.
     *
     * @param name the name of the category to delete
     */
    void deleteCategory(String name);

    /**
     * Checks if a category already exists in the data source.
     *
     * @param category the category to check
     * @return true if the category exists, false otherwise
     */
    boolean doesCategoryExist(Category category);
}