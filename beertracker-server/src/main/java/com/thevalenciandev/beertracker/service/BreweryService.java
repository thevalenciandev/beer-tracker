package com.thevalenciandev.beertracker.service;

import com.thevalenciandev.beertracker.domain.Brewery;
import com.thevalenciandev.beertracker.repository.BreweryRepository;
import org.springframework.stereotype.Service;

@Service
public class BreweryService {

    private final BreweryRepository breweryRepository;

    public BreweryService(BreweryRepository breweryRepository) {
        this.breweryRepository = breweryRepository;
    }

    public Brewery getBreweryDetails(Long breweryId) {
        return breweryRepository.findById(breweryId)
                .orElseThrow(BreweryNotFoundException::new);
    }
}
