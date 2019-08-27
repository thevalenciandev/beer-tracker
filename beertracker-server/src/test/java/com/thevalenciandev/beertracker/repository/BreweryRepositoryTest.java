package com.thevalenciandev.beertracker.repository;

import com.thevalenciandev.beertracker.domain.Brewery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@RunWith(SpringRunner.class)
public class BreweryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BreweryRepository breweryRepository;

    @Test
    public void canRetrieveBreweriesByName() {

        Brewery existingBrewery = populateDbWith(new Brewery(null, "Adnams"));

        Brewery retrievedBrewery = breweryRepository.findByName("Adnams").get();

        assertThat(retrievedBrewery).isEqualTo(existingBrewery);
    }

    private Brewery populateDbWith(Brewery brewery) {
        return entityManager.persistFlushFind(brewery);
    }

}