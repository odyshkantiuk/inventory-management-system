package com.inventory_management_system.model;
/**
 * Category model class.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class Category {
    private int id;
    private String name;
    private String description;

    /**
     * Constructs a Category object with the specified id, name, and description.
     *
     * @param id          the unique identifier for the category
     * @param name        the name of the category
     * @param description the description of the category
     */
    public Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}

