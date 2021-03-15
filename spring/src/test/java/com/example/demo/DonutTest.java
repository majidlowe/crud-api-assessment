package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import java.sql.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class DonutTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    DonutRepository repository;

    @Test
    @Transactional
    @Rollback
    public void endpointOneTest() throws Exception{
        String jsonCompare = ("{\"id\":1,\"name\":\"glazed\",\"topping\":\"sugar\",\"expiration\":\"2021-03-14\"}");
        MockHttpServletRequestBuilder request = post("/donuts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCompare);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("glazed")))
                .andExpect(jsonPath("$.topping", is("sugar")))
                .andExpect(jsonPath("$.expiration",is("2021-03-14")));
    }
    @Test
    @Transactional
    @Rollback
    public void endpointTwoTest() throws Exception{
        Donut glazedDonut = new Donut();
        glazedDonut.setTopping("sugar");
        glazedDonut.setName("glazed");
        glazedDonut.setExpiration(Date.valueOf("2021-03-14"));
        long idNumber = this.repository.save(glazedDonut).getId();



        this.mvc.perform(get("/donuts/" + idNumber)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("glazed")))
                .andExpect(jsonPath("$.topping", is("sugar")))
                .andExpect(jsonPath("$.expiration",is("2021-03-14")));
    }
    @Test
    @Transactional
    @Rollback
    public void endpointThreeTest() throws Exception{
        Donut sprinkledDonut = new Donut();
        sprinkledDonut.setName("Sprinkled");
        sprinkledDonut.setExpiration((Date.valueOf("2021-03-14")));
        sprinkledDonut.setTopping("Sprinkles");
        String json = ("{\"name\":\"Glazed\",\"topping\":\"glaze\",\"expiration\":\"2021-03-14\"}");
        Donut idNumber = this.repository.save(sprinkledDonut);

        MockHttpServletRequestBuilder request = patch("/donuts/" + idNumber.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Sprinkled")))
                .andExpect(jsonPath("$.topping", is("Sprinkles")))
                .andExpect(jsonPath("$.expiration",is("2021-03-14")));

    }
    @Test
    @Transactional
    @Rollback
    public void endPointFourTest() throws Exception{
        Donut sprinkledDonut = new Donut();
        sprinkledDonut.setName("Sprinkled");
        sprinkledDonut.setExpiration((Date.valueOf("2021-03-14")));
        sprinkledDonut.setTopping("Sprinkles");
        Donut idNumber = this.repository.save(sprinkledDonut);
        MockHttpServletRequestBuilder request = delete("/donuts/" + idNumber.getId().toString())
                .contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk());


    }@Test
    @Rollback
    @Transactional
    public void endpointFiveTest() throws Exception{
        Donut myDonut = new Donut();
        myDonut.setTopping("plain");
        myDonut.setExpiration(Date.valueOf("2021-03-14"));
        myDonut.setName("gross old donut");
        this.repository.save(myDonut);

        this.mvc.perform(get("/donuts")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("gross old donut")))
                .andExpect(jsonPath("$[0].topping", is("plain")))
                .andExpect(jsonPath("$[0].expiration", is("2021-03-14")));

    }
}
