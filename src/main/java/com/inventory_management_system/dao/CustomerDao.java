package com.inventory_management_system.dao;

import com.inventory_management_system.model.Customer;

import java.util.List;
/**
 * Customer dao interface.
 *
 * @author Oleksandr Dyshkantiuk
 */
public interface CustomerDao {
    /**
     * Retrieves all customers from the data source.
     *
     * @return a list of all customers
     */
    List<Customer> getAllCustomers();

    /**
     * Retrieves filtered customers from the data source based on the provided filters.
     *
     * @param filterName    the name filter
     * @param filterEmail   the email filter
     * @param filterPhone   the phone filter
     * @param filterAddress the address filter
     * @return a list of filtered customers
     */
    List<Customer> getFilteredCustomers(String filterName, String filterEmail, String filterPhone, String filterAddress);

    /**
     * Retrieves a customer from the data source based on its id.
     *
     * @param id the id of the customer to retrieve
     * @return the customer with the specified id, or null if not found
     */
    Customer getCustomerById(int id);

    /**
     * Retrieves a customer from the data source based on its name.
     *
     * @param name the name of the customer to retrieve
     * @return the customer with the specified name, or null if not found
     */
    Customer getCustomerByName(String name);

    /**
     * Adds a new customer to the data source.
     *
     * @param customer the customer to add
     */
    void addCustomer(Customer customer);

    /**
     * Updates an existing customer in the data source.
     *
     * @param customer the customer to update
     */
    void updateCustomer(Customer customer);

    /**
     * Updates multiple customers in the data source.
     *
     * @param customers the list of customers to update
     */
    void updateCustomers(List<Customer> customers);

    /**
     * Deletes a customer from the data source based on its id.
     *
     * @param id the id of the customer to delete
     */
    void deleteCustomer(int id);

    /**
     * Checks if a customer already exists in the data source.
     *
     * @param customer the customer to check
     * @return true if the customer exists, false otherwise
     */
    boolean doesCustomerExist(Customer customer);
}
