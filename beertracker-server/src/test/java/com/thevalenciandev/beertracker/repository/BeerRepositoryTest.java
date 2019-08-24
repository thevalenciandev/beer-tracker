package com.thevalenciandev.beertracker.repository;

import com.thevalenciandev.beertracker.domain.Beer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BeerRepositoryTest {

    @Autowired
    private BeerRepository repository;

    @Test
    public void canRetrieveBeerById() {

        repository.save(new Beer(1L, "Innovation IPA", "IPA", 6.7));

        Beer beer = repository.findById(1L).get();

        assertThat(beer.getId()).isEqualTo(1L);
        assertThat(beer.getName()).isEqualTo("Innovation IPA");
        assertThat(beer.getType()).isEqualTo("IPA");
        assertThat(beer.getABV()).isEqualTo(6.7);
    }

}