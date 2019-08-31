package com.thevalenciandev.beertracker.controller;

import com.thevalenciandev.beertracker.domain.Brewery;
import com.thevalenciandev.beertracker.service.BreweryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@WebMvcTest(BreweryController.class)
@RunWith(SpringRunner.class)
public class BreweryControllerTest {

    @MockBean
    public BreweryService breweryService;

    @Autowired
    public BreweryController breweryController;

    @Test
    public void canRetrieveBreweryById() {

        Brewery storedBrewery = new Brewery(1L, "Adnams");
        given(breweryService.getBreweryDetails(1L)).willReturn(storedBrewery);

        Brewery retrievedBrewery = breweryController.findOne(1L);

        assertThat(retrievedBrewery).isEqualTo(storedBrewery);
    }

}