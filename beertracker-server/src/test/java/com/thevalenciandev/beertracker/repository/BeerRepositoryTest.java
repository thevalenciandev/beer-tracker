package com.thevalenciandev.beertracker.repository;

import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.domain.Brewery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // clean DB after each test
public class BeerRepositoryTest {

    @Autowired
    private BeerRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void canRetrieveBeerById() {

        Beer savedBeer = entityManager.persistFlushFind(new Beer(null, "Innovation", "IPA", 6.7,
                                                                    new Brewery(null, "Adnams")));

        Beer retrievedBeer = repository.findById(1L).get();

        assertThat(retrievedBeer).isEqualTo(savedBeer);
    }

    @Test
    public void canRetrieveBeerByName() {

        Beer savedBeer = entityManager.persistFlushFind(beerOfName("Innovation"));

        Beer retrievedBeer = repository.findByName("Innovation").get();

        assertThat(retrievedBeer).isEqualTo(savedBeer);
    }

    @Test
    public void canRetrieveAllBeers() {
        Beer savedBeer1 = entityManager.persistFlushFind(new Beer(null, "Innovation", "IPA", 6.7,
                                                            new Brewery(null, "Adnams")));
        Beer savedBeer2 = entityManager.persistFlushFind(new Beer(null, "Punk", "IPA", 5.6,
                                                            new Brewery(null, "BrewDog")));
        // TODO: two beers by the same Brewery blow up this test because of the Unique constraint... look at it later

        assertThat(repository.findAll()).containsExactlyInAnyOrder(savedBeer1, savedBeer2);
    }

    @Test
    public void canCreateNewBeersWithNewBrewery() {
        Beer beerToCreate = new Beer(null, "London Pride", "Ale", 4.7,
                                        new Brewery(null, "Adnams"));

        Beer createdBeer = repository.save(beerToCreate);

        assertThat(createdBeer.getId()).isGreaterThan(0);
        assertThat(createdBeer).isEqualToIgnoringGivenFields(beerToCreate, "id");
    }

    //TODO: canCreateNewBeersWithExistingBrewery

    private Beer beerOfName(String name) {
        return Beer.builder().name(name).build();
    }

}