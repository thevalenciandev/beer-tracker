package com.thevalenciandev.beertracker.controller;

import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.service.BeerService;
import org.springframework.web.bind.annotation.*;

@RestController
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/beers")
    private Iterable<Beer> getAllBeers() {
        return beerService.getAllBeers();
    }

    @GetMapping("/beers/{id}")
    private Beer getBeer(@PathVariable Long id) {
        return beerService.getBeerDetails(id);
    }

    @PostMapping("/beers")
    private Beer newBeer(@RequestBody Beer newBeer) {
        return beerService.create(newBeer);
    }
}
