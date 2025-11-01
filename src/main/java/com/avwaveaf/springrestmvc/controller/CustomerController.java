package com.avwaveaf.springrestmvc.controller;

import com.avwaveaf.springrestmvc.controller.exception.NotFoundException;
import com.avwaveaf.springrestmvc.model.customer.Customer;
import com.avwaveaf.springrestmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CustomerController {
    public static final String CUSTOMER_BASE_URL = "/customer/";
    public static final String CUSTOMER_ID_URL = "/customer/{customerId}";

    private final CustomerService customerService;

    @PatchMapping(CUSTOMER_ID_URL)
    public ResponseEntity patchUpdateCustomerById(
            @PathVariable UUID customerId,
            @RequestBody Customer customer
    ) {

        customerService.patchUpdateById(customerId, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(CUSTOMER_ID_URL)
    public ResponseEntity deleteById(@PathVariable UUID customerId) {

        customerService.deleteCustomerById(customerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(CUSTOMER_ID_URL)
    public ResponseEntity updateCustomerById(
            @PathVariable("customerId") UUID id,
            @RequestBody Customer customer
    ) {
        customerService.updateById(id, customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + id.toString());

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping(CUSTOMER_BASE_URL)
    public ResponseEntity createNewCustomer(@RequestBody Customer customer) {
        Customer saved = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + saved.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(CUSTOMER_BASE_URL)
    public List<Customer> listCustomers() {
        log.debug("Getting list of customers (Controller)");
        return customerService.listCustomers();
    }

    @RequestMapping(CUSTOMER_ID_URL)
    public Customer getCustomerById(@PathVariable UUID customerId) {
        log.debug("Getting customer by id (Controller): {}", customerId);
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }
}
