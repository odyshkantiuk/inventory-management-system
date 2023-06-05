package com.inventory_management_system.dao;

import com.inventory_management_system.model.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> getAllCustomers();
    List<Customer> getFilteredCustomers(String filterName, String filterEmail, String filterPhone, String filterAddress);
    Customer getCustomerById(int id);
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void updateCustomers(List<Customer> customers);
    void deleteCustomer(int id);
    boolean doesCustomerExist(Customer customer);
}
