package com.thevalenciandev.beertracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.domain.Brewery;
import com.thevalenciandev.beertracker.service.BeerService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_UTF8_VALUE;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
@RunWith(SpringRunner.class)
@Import({BeerResourceAssembler.class}) // @Configuration classes are ignored by the @WebMvcTest annotation
//TODO: do I want to combine both Integration test and this one?
public class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BeerService beerService;

    @Test
    public void canRetrieveBeerById() throws Exception {
        given(beerService.getBeerDetails(anyLong())).willReturn(new Beer(1L, "Innovation IPA", "IPA", 6.7, new Brewery(1L, "Adnams")));

        mockMvc.perform(get("/beers/1").accept(HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("name").value("Innovation IPA"))
                .andExpect(jsonPath("style").value("IPA"))
                .andExpect(jsonPath("abv").value(6.7))
                .andExpect(jsonPath("brewery.id").value(1))
                .andExpect(jsonPath("brewery.name").value("Adnams"))
                .andExpect(jsonPath("_links.self.href").value("http://localhost/beers/1"))
                .andExpect(jsonPath("_links.brewery.href").value("http://localhost/beers/1/brewery"))
                .andExpect(jsonPath("_links.beers.href").value("http://localhost/beers"));
    }

    @Test
    public void canRetrieveAllBeers() throws Exception {
        given(beerService.getAllBeers()).willReturn(asList(Beer.builder().id(1L).name("beer-1").brewery(new Brewery(1L, null)).build(),
                                                           Beer.builder().id(2L).name("beer-2").brewery(new Brewery(1L, null)).build()));

        mockMvc.perform(get("/beers").accept(HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("_embedded.beers", hasSize(equalTo(2))))
                .andExpect(jsonPath("_embedded.beers[0].name", equalTo("beer-1")))
                .andExpect(jsonPath("_embedded.beers[0]._links.self.href").value("http://localhost/beers/1"))
                .andExpect(jsonPath("_embedded.beers[0]._links.brewery.href").value("http://localhost/beers/1/brewery"))
                .andExpect(jsonPath("_embedded.beers[1].name", equalTo("beer-2")))
                .andExpect(jsonPath("_embedded.beers[1]._links.self.href").value("http://localhost/beers/2"))
                .andExpect(jsonPath("_embedded.beers[1]._links.brewery.href").value("http://localhost/beers/2/brewery"))
                .andExpect(jsonPath("_links.self.href").value("http://localhost/beers"));
    }

    @Test
    public void canCreateNewBeers() throws Exception {
        Beer newBeer = new Beer(null, "London Pride", "Ale", 4.7, new Brewery(null, "Adnams"));

        given(beerService.create(newBeer)).willReturn(withId(1L, newBeer));

        mockMvc.perform(post("/beers/").contentType(APPLICATION_JSON).content(asJson(newBeer)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(CONTENT_TYPE, HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("name").value("London Pride"))
                .andExpect(jsonPath("style").value("Ale"))
                .andExpect(jsonPath("abv").value(4.7))
                .andExpect(jsonPath("brewery.id").value(1))
                .andExpect(jsonPath("brewery.name").value("Adnams"))
                .andExpect(jsonPath("_links.self.href").value("http://localhost/beers/1"))
                .andExpect(jsonPath("_links.brewery.href").value("http://localhost/beers/1/brewery"))
                .andExpect(jsonPath("_links.beers.href").value("http://localhost/beers"));

    }

    private String asJson(Beer newBeer) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(newBeer);
    }

    private Beer withId(long id, Beer newBeer) {
        return new Beer(id, newBeer.getName(), newBeer.getStyle(), newBeer.getABV(), new Brewery(id, newBeer.getBrewery().getName()));
    }

    @Test
    public void throwsExceptionUponBeerIdNotFound() throws Exception {
        given(beerService.getBeerDetails(anyLong())).willThrow(BeerNotFoundException.class);

        mockMvc.perform(get("/beers/666"))
                .andExpect(status().isNotFound());

    }

}
