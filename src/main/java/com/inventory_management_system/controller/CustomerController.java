package com.inventory_management_system.controller;

import com.inventory_management_system.dao.CustomerDao;
import com.inventory_management_system.dao.impl.CustomerDaoImpl;
import com.inventory_management_system.model.Customer;

import java.util.List;

public class CustomerController {
    private final CustomerDao customerDao;

    public CustomerController() {
        customerDao = new CustomerDaoImpl();
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public Customer getCustomerById(int id) {
        return customerDao.getCustomerById(id);
    }

    public void addCustomer(Customer customer) {
        customerDao.addCustomer(customer);
    }

    public void updateCustomer(Customer customer) {
        customerDao.updateCustomer(customer);
    }

    public void deleteCustomer(int id) {
        customerDao.deleteCustomer(id);
    }
}