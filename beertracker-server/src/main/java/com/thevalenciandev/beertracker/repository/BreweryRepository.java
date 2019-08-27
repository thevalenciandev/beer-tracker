package com.thevalenciandev.beertracker.repository;

import com.thevalenciandev.beertracker.domain.Brewery;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BreweryRepository extends CrudRepository<Brewery, Long> {

    Optional<Brewery> findByName(String name);
}
