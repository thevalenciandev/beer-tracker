package com.thevalenciandev.beertracker.repository;

import com.thevalenciandev.beertracker.domain.Brewery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.PersistenceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

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

    @Test
    public void cannotCreateTwoBreweriesWithTheSameName() {

        Brewery existingBrewery = populateDbWith(new Brewery(null, "Adnams"));

        Throwable thrown = catchThrowable(() -> createNew(new Brewery(null, "Adnams")));

        assertThat(thrown).isInstanceOf(PersistenceException.class);
    }

    private void createNew(Brewery brewery) {
        breweryRepository.save(brewery);
        entityManager.flush();
    }

    private Brewery populateDbWith(Brewery brewery) {
        return entityManager.persistFlushFind(brewery);
    }

}