package com.thevalenciandev.beertracker.service;

import com.thevalenciandev.beertracker.domain.Brewery;
import com.thevalenciandev.beertracker.repository.BreweryRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class BreweryServiceTest {

    @Mock
    BreweryRepository breweryRepository;
    BreweryService breweryService;

    @Before
    public void setUp() {
        breweryService = new BreweryService(breweryRepository);
    }

    @Test
    public void canRetrieveBreweryById() {

        Brewery storedBrewery = new Brewery(1L, "BrewDog");
        given(breweryRepository.findById(1L)).willReturn(Optional.of(storedBrewery));

        Brewery brewery = breweryService.getBreweryDetails(1L);

        assertThat(brewery).isEqualTo(storedBrewery);
    }

}