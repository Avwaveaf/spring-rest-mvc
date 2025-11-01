package com.avwaveaf.springrestmvc.controller;

import com.avwaveaf.springrestmvc.controller.exception.NotFoundException;
import com.avwaveaf.springrestmvc.model.beer.Beer;
import com.avwaveaf.springrestmvc.service.BeerService;
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
public class BeerController {
    public static final String BEER_BASE_URL = "/beer/";
    public static final String BEER_ID_URL = "/beer/{beerId}";
    private final BeerService beerService;

    @PatchMapping(BEER_ID_URL)
    public ResponseEntity updateBeerByIdPatch(
            @PathVariable UUID beerId,
            @RequestBody Beer beer
    ) {
        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_ID_URL)
    public ResponseEntity deleteById(@PathVariable UUID beerId) {

        beerService.deleteBeerById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_ID_URL)
    public ResponseEntity updateById(
            @PathVariable UUID beerId,
            @RequestBody Beer beer
    ) {
        beerService.updateByBeerId(beerId, beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + beerId.toString());

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping(BEER_BASE_URL)
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = BEER_BASE_URL, method = RequestMethod.GET)
    public List<Beer> listBeers() {
        log.debug("Getting list of beers (Controller)");
        return beerService.listBeers();
    }

    @RequestMapping(value = BEER_ID_URL, method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID id) {
        log.debug("Getting beer by id (Controller): {}", id);
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }
}
