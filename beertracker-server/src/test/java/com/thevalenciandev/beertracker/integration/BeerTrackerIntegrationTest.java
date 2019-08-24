package com.thevalenciandev.beertracker.integration;

import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BeerTrackerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BeerRepository beerRepository;

    @Test
    void canRetrieveBeerInfoById() {

        create(new Beer("Innovation IPA", "IPA", 6.7));

        ResponseEntity<Beer> response = get("/beers/1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("Innovation IPA");
        assertThat(response.getBody().getType()).isEqualTo("IPA");
        assertThat(response.getBody().getABV()).isEqualTo(6.7);
    }

    private ResponseEntity<Beer> get(String url) {
        return restTemplate.getForEntity(url, Beer.class);
    }

    private void create(Beer beer) {
        beerRepository.save(beer);
    }
}
