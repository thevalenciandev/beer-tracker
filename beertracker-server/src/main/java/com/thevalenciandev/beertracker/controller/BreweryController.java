package com.thevalenciandev.beertracker.controller;

import com.thevalenciandev.beertracker.domain.Brewery;
import com.thevalenciandev.beertracker.service.BeerService;
import com.thevalenciandev.beertracker.service.BreweryService;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class BreweryController {

    private final BreweryService breweryService;
    private final BeerService beerService;

    public BreweryController(BreweryService breweryService, BeerService beerService) {
        this.breweryService = breweryService;
        this.beerService = beerService;
    }

    @GetMapping("/breweries/{id}")
    public Resource<Brewery> findOne(@PathVariable Long id) {

        Brewery brewery = breweryService.getBreweryDetails(id);

        return new Resource<>(brewery, linkTo(methodOn(BreweryController.class).findOne(id)).withSelfRel());
    }

    @GetMapping("/beers/{beerId}/brewery")
    public Resource<Brewery> findBreweryForBeer(@PathVariable("beerId") Long beerId) {

        Brewery brewery = beerService.getBeerDetails(beerId).getBrewery();

        return new Resource<>(brewery, linkTo(methodOn(BreweryController.class).findOne(brewery.getId())).withSelfRel());
    }
}
