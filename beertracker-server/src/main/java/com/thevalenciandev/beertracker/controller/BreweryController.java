package com.thevalenciandev.beertracker.controller;

import com.thevalenciandev.beertracker.domain.Brewery;
import com.thevalenciandev.beertracker.service.BeerService;
import com.thevalenciandev.beertracker.service.BreweryService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class BreweryController {

    private final BreweryService breweryService;
    private final BeerService beerService;
    private final BreweryResourceAssembler breweryResourceAssembler;

    public BreweryController(BreweryService breweryService, BeerService beerService, BreweryResourceAssembler breweryResourceAssembler) {
        this.breweryService = breweryService;
        this.beerService = beerService;
        this.breweryResourceAssembler = breweryResourceAssembler;
    }

    @GetMapping("/breweries/{id}")
    public Resource<Brewery> findOne(@PathVariable Long id) {

        Brewery brewery = breweryService.getBreweryDetails(id);

        return breweryResourceAssembler.toResource(brewery);
    }

    @GetMapping("/breweries")
    public Resources<Resource<Brewery>> findAll() {

        List<Resource<Brewery>> breweries = stream(breweryService.getAllBreweries().spliterator(), false)
                .map(breweryResourceAssembler::toResource)
                .collect(toList());

        return new Resources<>(breweries, linkTo(methodOn(BreweryController.class).findAll()).withSelfRel());
    }

    @GetMapping("/beers/{beerId}/brewery")
    public Resource<Brewery> findBreweryForBeer(@PathVariable("beerId") Long beerId) {

        Brewery brewery = beerService.getBeerDetails(beerId).getBrewery();

        return breweryResourceAssembler.toResource(brewery);
    }
}
