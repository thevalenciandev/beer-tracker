package com.thevalenciandev.beertracker.controller;

import com.thevalenciandev.beertracker.domain.Beer;
import com.thevalenciandev.beertracker.service.BeerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BeerService beerService;

    @Test
    public void canRetrieveBeerById() throws Exception {
        given(beerService.getBeerDetails(anyLong())).willReturn(new Beer("Innovation IPA", "IPA", 6.7));

        mockMvc.perform(get("/beer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Innovation IPA"))
                .andExpect(jsonPath("type").value("IPA"))
                .andExpect(jsonPath("abv").value(6.7));
    }

    @Test
    public void throwsExceptionUponBeerIdNotFound() throws Exception {
        given(beerService.getBeerDetails(anyLong())).willThrow(BeerNotFoundException.class);

        mockMvc.perform(get("/beer/666"))
                .andExpect(status().isNotFound());

    }
}
