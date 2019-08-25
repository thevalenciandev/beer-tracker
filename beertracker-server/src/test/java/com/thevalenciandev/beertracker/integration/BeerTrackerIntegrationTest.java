package com.thevalenciandev.beertracker.integration;

import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.repository.BeerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD) // reset DB after each test
public class BeerTrackerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BeerRepository beerRepository;

    @Test
    public void canRetrieveBeerInfoById() {

        Beer beer = new Beer(null, "Innovation IPA", "IPA", 6.7);
        populateDbWith(beer);

        ResponseEntity<Beer> response = get("/beers/1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(beer);
    }

    @Test
    public void canRetrieveAllBeersInfo() {

        Beer beer1 = new Beer(null, "Innovation IPA", "IPA", 6.7);
        Beer beer2 = new Beer(null, "Wild Hop", "Amber", 4.8);
        populateDbWith(beer1);
        populateDbWith(beer2);

        ResponseEntity<Iterable<Beer>> response = getAll("/beers");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsExactlyInAnyOrder(beer1, beer2);
    }

    @Test
    public void canCreateNewBeers() {

        Beer newBeer = new Beer(null, "London Pride", "Ale", 5.2);

        post(newBeer, "/beers");

        ResponseEntity<Beer> response = get("/beers/1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isGreaterThan(0L);
        assertThat(response.getBody()).isEqualToIgnoringGivenFields(newBeer, "id");
    }

    private ResponseEntity<Beer> post(Beer newBeer, String url) {
        return restTemplate.postForEntity(url, newBeer, Beer.class);
    }

    private ResponseEntity<Iterable<Beer>> getAll(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<Beer>>() {});
    }

    private ResponseEntity<Beer> get(String url) {
        return restTemplate.getForEntity(url, Beer.class);
    }

    private void populateDbWith(Beer beer) {
        beerRepository.save(beer);
    }
}
