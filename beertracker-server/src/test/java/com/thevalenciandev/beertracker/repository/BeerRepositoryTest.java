package com.thevalenciandev.beertracker.repository;

import com.thevalenciandev.beertracker.domain.Beer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BeerRepositoryTest {

    @Autowired
    private BeerRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void canRetrieveBeerById() {

        Beer savedBeer = entityManager.persistFlushFind(new Beer("Innovation IPA", "IPA", 6.7));

        Beer beer = repository.findById(1L).get();

        assertThat(beer.getName()).isEqualTo(savedBeer.getName());
        assertThat(beer.getType()).isEqualTo(savedBeer.getType());
        assertThat(beer.getABV()).isEqualTo(savedBeer.getABV());
    }

}