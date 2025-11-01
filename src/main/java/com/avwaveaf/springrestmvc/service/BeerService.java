package com.avwaveaf.springrestmvc.service;

import com.avwaveaf.springrestmvc.model.beer.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    List<Beer> listBeers();

    Optional<Beer> getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateByBeerId(UUID id, Beer beer);

    void deleteBeerById(UUID id);

    void patchBeerById(UUID id, Beer beer);
}
