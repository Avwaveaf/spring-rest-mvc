package com.avwaveaf.springrestmvc.controller;

import com.avwaveaf.springrestmvc.model.beer.Beer;
import com.avwaveaf.springrestmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
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
    private final BeerService beerService;

    @PatchMapping("/beer/{id}")
    public ResponseEntity updateBeerByIdPatch(
            @PathVariable UUID id,
            @RequestBody Beer beer
    ){
        beerService.patchBeerById(id, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/beer/{id}")
    public ResponseEntity deleteById(@PathVariable UUID id){

        beerService.deleteBeerById(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/beer/{id}")
    public ResponseEntity updateById(
            @PathVariable UUID id,
            @RequestBody Beer beer
    ) {
        beerService.updateByBeerId(id, beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + id.toString());

        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/beer")
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/beer", method = RequestMethod.GET)
    public List<Beer> listBeers() {
        log.debug("Getting list of beers (Controller)");
        return beerService.listBeers();
    }

    @RequestMapping(value = "/beer/{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID id) {
        log.debug("Getting beer by id (Controller): {}", id);
        return beerService.getBeerById(id);
    }
}
