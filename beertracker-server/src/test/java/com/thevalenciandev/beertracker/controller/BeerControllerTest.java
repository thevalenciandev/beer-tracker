package com.thevalenciandev.beertracker.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void canRetrieveBeerById() throws Exception {
        mockMvc.perform(get("/beer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Innovation IPA"))
                .andExpect(jsonPath("type").value("IPA"))
                .andExpect(jsonPath("ABV").value(6.7));
    }
}
