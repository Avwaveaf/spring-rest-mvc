package com.avwaveaf.springrestmvc.controller;

import com.avwaveaf.springrestmvc.model.beer.Beer;
import com.avwaveaf.springrestmvc.service.BeerService;
import com.avwaveaf.springrestmvc.service.BeerServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
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

//@SpringBootTest

/// Bring up the context that spring provide
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper; // jackson object mapper to compose a json object

    @MockitoBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @BeforeEach
    void setUp() {
        // restart every test, so we can modify freely
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void getBeerById() throws Exception {
        /// Given
        Beer testBeer = beerServiceImpl.listBeers().get(0);
        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        /// When
        mockMvc.perform(get("/beer/" + testBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                /// Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
    }

    @Test
    void getListBeer() throws Exception {
        /// Given
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());
        int listSize = beerServiceImpl.listBeers().size();

        /// When
        mockMvc.perform(get("/beer").accept(MediaType.APPLICATION_JSON))
                /// Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(listSize)));
    }

    @Test
    void testCreateBeer() throws Exception {
        /// Given
        Beer beer = beerServiceImpl.listBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);
        beer.setCreatedDate(null);
        beer.setUpdateDate(null);

        given(beerService.saveNewBeer(any(Beer.class))).willReturn(beerServiceImpl.listBeers().get(1));

        /// When
        mockMvc.perform(post("/beer")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer))
                )
                /// Then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }
}