package com.thevalenciandev.beertracker.repository;

import com.thevalenciandev.beertracker.domain.Beer;
import org.springframework.data.repository.CrudRepository;

public interface BeerRepository extends CrudRepository<Beer, Long> {

}
