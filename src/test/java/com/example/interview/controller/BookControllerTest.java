package com.example.interview.controller;

import com.example.interview.InterviewTaskApplication;
import com.example.interview.exception.UnableToGetDateException;
import com.example.interview.excteption.UnableToReadBooksException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = InterviewTaskApplication.class)
//@WebAppConfiguration
@TestPropertySource("classpath:test.properties")
class BookControllerTest {
    @Value("${test.url.books.list.right}")
    String urlRightBooksList;

    @Value("${test.url.books.list.wrong}")
    String urlWrongBooksList;

    private MockMvc mockMvc;


    @BeforeEach
    protected void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    protected static String convertMapToJson(String name, String userName) {
        Map<String, String> map = Map.of("name", name, "userName", userName);
        try {
            return new ObjectMapper().writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getListBookTest() {
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(urlRightBooksList).accept(MediaType.APPLICATION_JSON_VALUE))
                    .andReturn();
        } catch (Exception e) {
            throw new UnableToReadBooksException(e.getMessage(), e);
        }
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void getListBookWrongTest() {
        MvcResult mvcResult;
        try {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(urlWrongBooksList).accept(MediaType.APPLICATION_JSON))
                    .andReturn();
        } catch (Exception e) {
            throw new UnableToGetDateException(e.getMessage(), e);
        }
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }
}