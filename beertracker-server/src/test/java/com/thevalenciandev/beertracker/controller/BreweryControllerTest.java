package com.thevalenciandev.beertracker.controller;

import com.thevalenciandev.beertracker.domain.Brewery;
import com.thevalenciandev.beertracker.service.BeerService;
import com.thevalenciandev.beertracker.service.BreweryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_UTF8_VALUE;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {BreweryController.class, BeerController.class})
@RunWith(SpringRunner.class)
@Import({BeerResourceAssembler.class, BreweryResourceAssembler.class})
public class BreweryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    public BreweryService breweryService;

    @MockBean
    public BeerService beerService;

    @Test
    public void canRetrieveBreweryById() throws Exception {

        Brewery storedBrewery = new Brewery(1L, "BrewDog");
        given(breweryService.getBreweryDetails(1L)).willReturn(storedBrewery);

        mockMvc.perform(get("/breweries/1").accept(HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("name").value("BrewDog"))
                .andExpect(jsonPath("_links.self.href").value("http://localhost/breweries/1"))
                .andExpect(jsonPath("_links.breweries.href").value("http://localhost/breweries"));
    }

    @Test
    public void canRetrieveAllBreweries() throws Exception {

        given(breweryService.getAllBreweries()).willReturn(asList(new Brewery(1L, "brewery-1"), new Brewery(2L, "brewery-2")));

        mockMvc.perform(get("/breweries").accept(HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("_embedded.breweries", hasSize(equalTo(2))))
                .andExpect(jsonPath("_embedded.breweries[0].name", equalTo("brewery-1")))
                .andExpect(jsonPath("_embedded.breweries[0]._links.self.href").value("http://localhost/breweries/1"))
                .andExpect(jsonPath("_embedded.breweries[1].name", equalTo("brewery-2")))
                .andExpect(jsonPath("_embedded.breweries[1]._links.self.href").value("http://localhost/breweries/2"))
                .andExpect(jsonPath("_links.self.href").value("http://localhost/breweries"));
    }

}