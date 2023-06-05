package com.inventory_management_system.controller;

import com.inventory_management_system.dao.CustomerDao;
import com.inventory_management_system.dao.impl.CustomerDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
import com.inventory_management_system.model.Customer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * The CustomerController class handles operations related to customer management.
 *
 * @author Oleksandr Dyshkantiuk
 */
public class CustomerController {
    private final CustomerDao customerDao;

    /**
     * Constructs a CustomerController object and initializes the CustomerDao.
     */
    public CustomerController() {
        customerDao = new CustomerDaoImpl();
    }

    /**
     * Retrieves all customers.
     *
     * @return A list of all customers.
     */
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    /**
     * Retrieves filtered customers based on provided filters.
     *
     * @param filterName    The name filter.
     * @param filterEmail   The email filter.
     * @param filterPhone   The phone filter.
     * @param filterAddress The address filter.
     * @return A list of filtered customers.
     */
    public List<Customer> getFilteredCustomers(String filterName, String filterEmail, String filterPhone, String filterAddress) {
        return customerDao.getFilteredCustomers(filterName, filterEmail, filterPhone, filterAddress);
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The customer with the specified ID.
     */
    public Customer getCustomerById(int id) {
        return customerDao.getCustomerById(id);
    }

    /**
     * Retrieves a customer by their name.
     *
     * @param name The name of the customer to retrieve.
     * @return The customer with the specified name.
     */
    public Customer getCustomerByName(String name) {
        return customerDao.getCustomerByName(name);
    }

    /**
     * Adds a new customer.
     *
     * @param customer The customer to add.
     * @throws AlreadyExistsException If the customer already exists.
     */
    public void addCustomer(Customer customer) {
        if (!customerDao.doesCustomerExist(customer)) {
            customerDao.addCustomer(customer);
        } else {
            new AlreadyExistsException("Customer");
        }
    }

    /**
     * Updates an existing customer.
     *
     * @param customer The customer to update.
     */
    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }

    /**
     * Updates multiple customers.
     *
     * @param customers A list of customers to update.
     * @throws AlreadyExistsException If any customer already exists or if any customer name is a duplicate.
     */
    public void updateCustomers(List<Customer> customers) {
        Set<String> nameSet = new HashSet<>();
        for (Customer customer : customers){
            if (customerDao.doesCustomerExist(customer)) {
                new AlreadyExistsException("Customer");
                return;
            } else if (!nameSet.add(customer.getName())) {
                new AlreadyExistsException("Supplier");
                return;
            }
        }
        customerDao.updateCustomers(customers);
    }

    /**
     * Deletes a customer by their ID.
     *
     * @param id The ID of the customer to delete.
     */
    public void deleteCustomer(int id) {
        customerDao.deleteCustomer(id);
    }
}