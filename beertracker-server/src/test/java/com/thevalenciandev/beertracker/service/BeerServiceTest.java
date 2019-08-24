package com.thevalenciandev.beertracker.service;

import com.thevalenciandev.beertracker.controller.BeerNotFoundException;
import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.repository.BeerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
        given(beerRepository.findById(1L)).willReturn(Optional.of(new Beer("Innovation IPA", "IPA", 6.7)));

        Beer beer = beerService.getBeerDetails(1L);

        assertThat(beer.getName()).isEqualTo("Innovation IPA");
        assertThat(beer.getType()).isEqualTo("IPA");
        assertThat(beer.getABV()).isEqualTo(6.7);
    }

    @Test
    public void throwsExceptionUponUnknownId() {
        given(beerRepository.findById(666L)).willThrow(BeerNotFoundException.class);

        Throwable thrown = catchThrowable(() -> beerService.getBeerDetails(666L));

        assertThat(thrown).isInstanceOf(BeerNotFoundException.class);
    }
}