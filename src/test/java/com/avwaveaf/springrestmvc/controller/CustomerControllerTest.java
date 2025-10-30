package com.avwaveaf.springrestmvc.controller;

import com.avwaveaf.springrestmvc.model.customer.Customer;
import com.avwaveaf.springrestmvc.service.CustomerService;
import com.avwaveaf.springrestmvc.service.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @Test
    void getCustomerById() throws Exception {
        /// Given
        Customer testCustomer = customerServiceImpl.listCustomers().get(0);
        given(customerService.getCustomerById(testCustomer.getId())).willReturn(testCustomer);

        /// When
        mockMvc.perform(get("/customer/" + testCustomer.getId()).accept(MediaType.APPLICATION_JSON))
                /// Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())))
                .andExpect(jsonPath("$.customerName", is(testCustomer.getCustomerName())));
    }

    @Test
    void getCustomers() throws Exception {
        /// Given
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());
        int listSize = customerServiceImpl.listCustomers().size();

        ///  When
        mockMvc.perform(get("/customer").accept(MediaType.APPLICATION_JSON))
                /// Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(listSize)));
    }

    @Test
    void createNewCustomer() throws Exception {
        /// Given
        Customer cust = customerServiceImpl.listCustomers().get(0);
        cust.setCreatedDate(null);
        cust.setLastModifiedDate(null);
        cust.setVersion(null);
        cust.setId(null);

        given(customerService.saveNewCustomer(any(Customer.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        /// When
        mockMvc.perform(post("/customer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cust))
                )
                /// Then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

}