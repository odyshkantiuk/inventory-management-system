package com.inventory_management_system.controller;

import com.inventory_management_system.dao.CustomerDao;
import com.inventory_management_system.dao.impl.CustomerDaoImpl;
import com.inventory_management_system.exception.AlreadyExistsException;
import com.inventory_management_system.model.Customer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerController {
    private final CustomerDao customerDao;

    public CustomerController() {
        customerDao = new CustomerDaoImpl();
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public List<Customer> getFilteredCustomers(String filterName, String filterEmail, String filterPhone, String filterAddress) {
        return customerDao.getFilteredCustomers(filterName, filterEmail, filterPhone, filterAddress);
    }

    public Customer getCustomerById(int id) {
        return customerDao.getCustomerById(id);
    }

    public void addCustomer(Customer customer) {
        if (!customerDao.doesCustomerExist(customer)) {
            customerDao.addCustomer(customer);
        } else {
            new AlreadyExistsException("Customer");
        }
    }

    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }

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

    public void deleteCustomer(int id) {
        customerDao.deleteCustomer(id);
    }
}