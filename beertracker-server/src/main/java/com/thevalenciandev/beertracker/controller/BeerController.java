package com.thevalenciandev.beertracker.controller;

import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.service.BeerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beers")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("")
    private Iterable<Beer> getAllBeers() {
        return beerService.getAllBeers();
    }

    @GetMapping("/{id}")
    private Beer getBeer(@PathVariable Long id) {
        return beerService.getBeerDetails(id);
    }
}
