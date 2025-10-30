package com.avwaveaf.springrestmvc.controller;

import com.avwaveaf.springrestmvc.model.customer.Customer;
import com.avwaveaf.springrestmvc.service.CustomerService;
import lombok.AllArgsConstructor;
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
    private final CustomerService customerService;

    @PatchMapping("/customer/{id}")
    public ResponseEntity patchUpdateCustomerById(
            @PathVariable UUID id,
            @RequestBody Customer customer
    ){

        customerService.patchUpdateById(id, customer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/customer/{id}")
    public ResponseEntity deleteById(@PathVariable UUID id){

        customerService.deleteCustomerById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/customer/{customerId}")
    public ResponseEntity updateCustomerById(
            @PathVariable("customerId") UUID id,
            @RequestBody Customer customer
    ) {
        customerService.updateById(id, customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + id.toString());

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/customer")
    public ResponseEntity createNewCustomer(@RequestBody Customer customer) {
        Customer saved = customerService.saveNewCustomer(customer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customer/" + saved.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping("/customer")
    public List<Customer> listCustomers() {
        log.debug("Getting list of customers (Controller)");
        return customerService.listCustomers();
    }

    @RequestMapping("/customer/{customerId}")
    public Customer getCustomerById(@PathVariable UUID customerId) {
        log.debug("Getting customer by id (Controller): {}", customerId);
        return customerService.getCustomerById(customerId);
    }
}
