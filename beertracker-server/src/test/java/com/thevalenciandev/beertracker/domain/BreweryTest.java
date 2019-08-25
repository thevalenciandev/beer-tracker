package com.thevalenciandev.beertracker.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BreweryTest {

    @Test
    public void canCreateABrewery() {

        Brewery brewery = new Brewery(1L, "Adnams", "England");

        assertThat(brewery.getId()).isEqualTo(1L);
        assertThat(brewery.getName()).isEqualTo("Adnams");
        assertThat(brewery.getCountry()).isEqualTo("England");
    }
}
