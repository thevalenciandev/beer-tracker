package com.thevalenciandev.beertracker.service;

import com.thevalenciandev.beertracker.controller.BeerNotFoundException;
import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.repository.BeerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class BeerServiceTest {

    @Mock
    BeerRepository beerRepository;

    BeerService beerService;

    @Before
    public void setUp() {
        beerService = new BeerService(beerRepository);
    }

    @Test
    public void canRetrieveBeerById() {
        Beer savedBeer = new Beer(1L, "Innovation IPA", "IPA", 6.7);
        given(beerRepository.findById(1L)).willReturn(Optional.of(savedBeer));

        assertThat(beerService.getBeerDetails(1L)).isEqualTo(savedBeer);
    }

    @Test
    public void throwsExceptionUponUnknownId() {
        given(beerRepository.findById(666L)).willThrow(BeerNotFoundException.class);

        Throwable thrown = catchThrowable(() -> beerService.getBeerDetails(666L));

        assertThat(thrown).isInstanceOf(BeerNotFoundException.class);
    }

    @Test
    public void canRetrieveAllBeers() {
        Beer[] beers = {new Beer(1L, "Innovation IPA", "IPA", 6.7),
                new Beer(2L, "Wild Hop", "Amber", 4.8)};

        given(beerRepository.findAll()).willReturn(Arrays.asList(beers));

        assertThat(beerService.getAllBeers()).containsExactlyInAnyOrder(beers);
    }

    @Test
    public void canCreateNewBeers() {

        Beer beerToCreate = new Beer(null, "London Pride", "Ale", 4.7);

        given(beerRepository.save(beerToCreate)).willReturn(withId(1L, beerToCreate));

        assertThat(beerService.create(beerToCreate)).isEqualTo(withId(1L, beerToCreate));
    }

    private Beer withId(long id, Beer newBeer) {
        return new Beer(id, newBeer.getName(), newBeer.getType(), newBeer.getABV());
    }
}