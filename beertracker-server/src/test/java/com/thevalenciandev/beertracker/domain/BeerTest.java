package com.thevalenciandev.beertracker.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Beer POJO basic tests")
class BeerTest {

    @Test
    void canCreateBeerEntity() {
        Beer beer = new Beer(1L, "Innovation IPA", "IPA");
        assertThat(beer.getId()).isEqualTo(1);
        assertThat(beer.getName()).isEqualTo("Innovation IPA");
        assertThat(beer.getType()).isEqualTo("IPA");
    }
}
