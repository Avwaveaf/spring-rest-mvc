package com.avwaveaf.springrestmvc.service;

import com.avwaveaf.springrestmvc.model.beer.Beer;
import com.avwaveaf.springrestmvc.model.beer.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    {
        beerMap = new HashMap<>();
        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12345")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12345222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12345")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer4 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .upc("12345")
                .price(new BigDecimal("10.99"))
                .quantityOnHand(200)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer5 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Dragon's Breath")
                .beerStyle(BeerStyle.STOUT)
                .upc("12345")
                .price(new BigDecimal("15.99"))
                .quantityOnHand(150)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer6 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Mystic Porter")
                .beerStyle(BeerStyle.PORTER)
                .upc("12345")
                .price(new BigDecimal("14.99"))
                .quantityOnHand(180)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer7 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Golden Wheat")
                .beerStyle(BeerStyle.WHEAT)
                .upc("12345")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(220)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer8 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Red Devil")
                .beerStyle(BeerStyle.LAGER)
                .upc("12345")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(170)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer9 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Dark Knight")
                .beerStyle(BeerStyle.STOUT)
                .upc("12345")
                .price(new BigDecimal("16.99"))
                .quantityOnHand(140)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer10 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Hoppy Heaven")
                .beerStyle(BeerStyle.IPA)
                .upc("12345")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(190)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
        beerMap.put(beer4.getId(), beer4);
        beerMap.put(beer5.getId(), beer5);
        beerMap.put(beer6.getId(), beer6);
        beerMap.put(beer7.getId(), beer7);
        beerMap.put(beer8.getId(), beer8);
        beerMap.put(beer9.getId(), beer9);
        beerMap.put(beer10.getId(), beer10);
    }

    @Override
    public List<Beer> listBeers() {
        log.debug("Requested list of beers (Service)");
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Requested beer by id (Service): {}", id);
        return beerMap.get(id);
    }

    @Override
    public Beer saveNewBeer(Beer beer) {
        Beer saved = Beer.builder()
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .id(UUID.randomUUID())
                .build();
        beerMap.put(saved.getId(), saved);
        return saved;
    }

    @Override
    public void updateByBeerId(UUID id, Beer beer) {
        Beer existing = beerMap.get(id);
        existing.setBeerName(beer.getBeerName());
        existing.setBeerStyle(beer.getBeerStyle());
        existing.setUpc(beer.getUpc());
        existing.setPrice(beer.getPrice());
        existing.setQuantityOnHand(beer.getQuantityOnHand());
        existing.setUpdateDate(LocalDateTime.now());

        beerMap.put(id, existing);
    }

    @Override
    public void deleteBeerById(UUID id) {
        beerMap.remove(id);
    }

    @Override
    public void patchBeerById(UUID id, Beer beer) {
        Beer found = beerMap.get(id);

        if (StringUtils.hasText(beer.getBeerName())) {
            found.setBeerName(beer.getBeerName());
        }

        if (StringUtils.hasText(beer.getUpc())) {
            found.setUpc(beer.getUpc());
        }

        if (beer.getBeerStyle() != null) {
            found.setBeerStyle(beer.getBeerStyle());
        }

        if (beer.getPrice() != null) {
            found.setPrice(beer.getPrice());
        }

        if (beer.getQuantityOnHand() != null) {
            found.setQuantityOnHand(beer.getQuantityOnHand());
        }

        beerMap.put(id, found);
    }
}
