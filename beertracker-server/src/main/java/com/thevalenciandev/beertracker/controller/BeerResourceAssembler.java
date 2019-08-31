package com.thevalenciandev.beertracker.controller;

import com.thevalenciandev.beertracker.domain.Beer;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BeerResourceAssembler implements ResourceAssembler<Beer, Resource<Beer>> {

    @Override
    public Resource<Beer> toResource(Beer beer) {

        return new Resource<>(beer,
                linkTo(methodOn(BeerController.class).findOne(beer.getId())).withSelfRel(),
                linkTo(methodOn(BeerController.class).findAll()).withRel("beers"));
    }
}
