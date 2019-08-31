package com.thevalenciandev.beertracker.controller;

import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.service.BeerService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/beers")
public class BeerController {

    private final BeerService beerService;
    private final BeerResourceAssembler resourceAssembler;

    public BeerController(BeerService beerService, BeerResourceAssembler resourceAssembler) {
        this.beerService = beerService;
        this.resourceAssembler = resourceAssembler;
    }

    @GetMapping
    Resources<Resource<Beer>> getAllBeers() {

        List<Resource<Beer>> beerList = stream(beerService.getAllBeers().spliterator(), false)
                .map(resourceAssembler::toResource)
                .collect(toList());

        return new Resources<>(beerList, linkTo(methodOn(BeerController.class).getAllBeers()).withSelfRel());
    }

    @GetMapping("/{id}")
    Resource<Beer> findOne(@PathVariable Long id) {

        Beer beer = beerService.getBeerDetails(id);

        return resourceAssembler.toResource(beer);
    }

    @PostMapping
    private Resource<Beer> newBeer(@RequestBody Beer newBeer) {

        Beer beer = beerService.create(newBeer);

        return resourceAssembler.toResource(beer);
    }
}
