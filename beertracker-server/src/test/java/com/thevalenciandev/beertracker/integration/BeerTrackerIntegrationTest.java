package com.thevalenciandev.beertracker.integration;

import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.domain.Brewery;
import com.thevalenciandev.beertracker.repository.BeerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

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

        Beer beer = new Beer(null, "Innovation IPA", "IPA", 6.7, new Brewery(null, "Adnams"));
        populateDbWith(beer);

        ResponseEntity<Resource<Beer>> response = get("/beers/1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThatResponseContainsBeer(beer, response.getBody());
    }

    @Test
    public void canRetrieveAllBeersInfo() {

        Beer beer1 = new Beer(null, "Innovation", "IPA", 6.7, new Brewery(null, "Adnams"));
        Beer beer2 = new Beer(null, "Punk", "IPA", 5.6, new Brewery(null, "BrewDog"));
        populateDbWith(beer1);
        populateDbWith(beer2);
        // TODO: two beers by the same Brewery blow up this test because of the Unique constraint... look at it later

        ResponseEntity<Resources<Resource<Beer>>> response = getAll("/beers");

        Iterator<Resource<Beer>> beerResources = response.getBody().getContent().iterator();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThatResponseContainsBeer(beer1, beerResources.next());
        assertThatResponseContainsBeer(beer2, beerResources.next());
    }

    @Test
    public void canCreateNewBeers() {

        Beer newBeer = new Beer(null, "London Pride", "Ale", 4.7, new Brewery(null, "Adnams"));

        post(newBeer, "/beers");

        ResponseEntity<Resource<Beer>> response = get("/beers/1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThatResponseContainsBeer(newBeer, response.getBody());
    }

    private void assertThatResponseContainsBeer(Beer beer, Resource<Beer> body) {
        assertThat(body.getContent().getName()).isEqualTo(beer.getName());
        assertThat(body.getContent().getStyle()).isEqualTo(beer.getStyle());
        assertThat(body.getContent().getABV()).isEqualTo(beer.getABV());
    }

    private ResponseEntity<Resource> post(Beer newBeer, String url) {
        return restTemplate.postForEntity(url, newBeer, Resource.class);
    }

    private ResponseEntity<Resources<Resource<Beer>>> getAll(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Resources<Resource<Beer>>>() {});
    }

    private ResponseEntity<Resource<Beer>> get(String url) {
        return restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Beer>>() {});
    }

    private void populateDbWith(Beer beer) {
        beerRepository.save(beer);
    }
}
