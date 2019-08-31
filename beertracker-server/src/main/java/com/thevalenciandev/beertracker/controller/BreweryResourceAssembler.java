package com.thevalenciandev.beertracker.controller;

import com.thevalenciandev.beertracker.domain.Brewery;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BreweryResourceAssembler implements ResourceAssembler<Brewery, Resource<Brewery>> {

    @Override
    public Resource<Brewery> toResource(Brewery brewery) {

        return new Resource<>(brewery,
                linkTo(methodOn(BreweryController.class).findOne(brewery.getId())).withSelfRel(),
                linkTo(methodOn(BreweryController.class).findAll()).withRel("breweries"));
    }
}
