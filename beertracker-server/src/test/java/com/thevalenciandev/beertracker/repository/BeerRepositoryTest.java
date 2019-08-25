package com.thevalenciandev.beertracker.repository;

import com.thevalenciandev.beertracker.domain.Beer;
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

        Beer savedBeer = entityManager.persistFlushFind(new Beer(null, "Innovation IPA", "IPA", 6.7));

        Beer retrievedBeer = repository.findById(1L).get();

        assertThat(retrievedBeer).isEqualTo(savedBeer);
    }

    @Test
    public void canRetrieveBeerByName() {

        Beer savedBeer = entityManager.persistFlushFind(beerOfName("Innovation IPA"));

        Beer retrievedBeer = repository.findByName("Innovation IPA").get();

        assertThat(retrievedBeer).isEqualTo(savedBeer);
    }

    @Test
    public void canRetrieveAllBeers() {
        Beer savedBeer1 = entityManager.persistFlushFind(new Beer(null, "Innovation IPA", "IPA", 6.7));
        Beer savedBeer2 = entityManager.persistFlushFind(new Beer(null, "Wild Hop", "Amber", 4.8));

        assertThat(repository.findAll()).containsExactlyInAnyOrder(savedBeer1, savedBeer2);
    }

    @Test
    public void canCreateNewBeers() {
        Beer beerToCreate = new Beer(null, "London Pride", "Ale", 4.7);

        Beer createdBeer = repository.save(beerToCreate);

        assertThat(createdBeer.getId()).isGreaterThan(0);
        assertThat(createdBeer).isEqualToIgnoringGivenFields(beerToCreate, "id");
    }

    private Beer withId(long id, Beer beerToCreate) {
        return new Beer(id, beerToCreate.getName(), beerToCreate.getStyle(), beerToCreate.getABV());
    }

    private Beer beerOfName(String name) {
        return new Beer(null, name, "a-style", 12.3);
    }

}