package com.thevalenciandev.beertracker.repository;

import com.thevalenciandev.beertracker.domain.Beer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BeerRepository extends CrudRepository<Beer, Long> {

    Optional<Beer> findByName(String name);
}
