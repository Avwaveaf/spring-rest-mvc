package com.avwaveaf.springrestmvc.controller;

import com.avwaveaf.springrestmvc.model.beer.Beer;
import com.avwaveaf.springrestmvc.service.BeerService;
import com.avwaveaf.springrestmvc.service.BeerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest

/// Bring up the context that spring provide
@WebMvcTest(BeerController.class)
class BeerControllerTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;
    @Captor
    ArgumentCaptor<UUID> acUUID;
    @Captor
    ArgumentCaptor<Beer> acBeer;


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
        mockMvc.perform(get(BeerController.BEER_BASE_URL + testBeer.getId())
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
        mockMvc.perform(get(BeerController.BEER_BASE_URL).accept(MediaType.APPLICATION_JSON))
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
        mockMvc.perform(post(BeerController.BEER_BASE_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer))
                )
                /// Then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateBeer() throws Exception {
        /// Given
        Beer beer = beerServiceImpl.listBeers().get(0);

        /// When
        mockMvc.perform(put(BeerController.BEER_BASE_URL + beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer))
                )
                .andExpect(status().isNoContent());

        /// Then
        verify(beerService).updateByBeerId(any(UUID.class), any(Beer.class));
    }

    @Test
    void deleteBeer() throws Exception {
        /// Given
        Beer beer = beerServiceImpl.listBeers().get(0);

        /// When
        mockMvc.perform(delete(BeerController.BEER_BASE_URL + beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                )
                /// Then
                .andExpect(status().isNoContent());

        /// Then
        verify(beerService).deleteBeerById(acUUID.capture());
        assertThat(beer.getId()).isEqualTo(acUUID.getValue());
    }

    @Test
    void patchBeer() throws Exception {
        /// Given
        Beer beer = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        // we want to patch beerName only
        beerMap.put("beerName", "New Beer Name");
        /// When
        mockMvc.perform(patch(BeerController.BEER_BASE_URL + beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap))
                )
                .andExpect(status().isNoContent());

        /// Then
        verify(beerService).patchBeerById(acUUID.capture(), acBeer.capture());
        assertThat(beerMap.get("beerName")).isEqualTo(acBeer.getValue().getBeerName());
    }
}