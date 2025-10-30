package com.avwaveaf.springrestmvc.service;

import com.avwaveaf.springrestmvc.model.customer.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> listCustomers();
    Customer getCustomerById(UUID id);

    Customer saveNewCustomer(Customer customer);

    void updateById(UUID id, Customer customer);

    void deleteCustomerById(UUID id);

    void patchUpdateById(UUID id, Customer customer);
}
