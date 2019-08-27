package com.thevalenciandev.beertracker.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BeerTest {

    @Test
    public void canCreateBeerEntity() {
        Beer beer = new Beer(1L, "Innovation IPA", "IPA", 6.7,
                                new Brewery(1L, "Adnams"));

        assertThat(beer.getId()).isEqualTo(1L);
        assertThat(beer.getName()).isEqualTo("Innovation IPA");
        assertThat(beer.getStyle()).isEqualTo("IPA");
        assertThat(beer.getABV()).isEqualTo(6.7);

        assertThat(beer.getBrewery().getId()).isEqualTo(1L);
        assertThat(beer.getBrewery().getName()).isEqualTo("Adnams");
    }
}
