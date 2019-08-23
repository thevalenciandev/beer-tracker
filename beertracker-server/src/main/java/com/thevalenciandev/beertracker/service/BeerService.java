package com.thevalenciandev.beertracker.service;

import com.thevalenciandev.beertracker.controller.BeerNotFoundException;
import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.repository.BeerRepository;

public class BeerService {
    private final BeerRepository beerRepository;

    public BeerService(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    public Beer getBeerDetails(long id) {
        return beerRepository.findById(id).orElseThrow(BeerNotFoundException::new);
    }
}
