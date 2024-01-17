package com.example.interview.controller;

import com.example.interview.InterviewTaskApplication;
import com.example.interview.exception.UnableToGetDateException;
import com.example.interview.excteption.ExceptionMessage;
import com.example.interview.excteption.UnableToReadBooksException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = InterviewTaskApplication.class)
@WebAppConfiguration
@TestPropertySource("classpath:test.properties")
class BookControllerTest {
    @Value("${test.url.books.list.right}")
    String urlRightBooksList;

    @Value("${test.url.books.list.wrong}")
    String urlWrongBooksList;

    @Value("${test.url.books.reserving}")
    String urlReserving;

    @Value("${test.reserving.copy}")
    int copy;
    @Value("${test.reserving.bookdto.json}")
    String reserving;
    @Value("${test.reserving.name.book}")
    String nameBook;

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

    @Test
    void reserveBookTheSameUserTest() {
        int i = 0;
        MvcResult mvcResult;
        while (i <= 1) {
            try {
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(urlReserving)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(reserving))
                        .andReturn();
            } catch (Exception e) {
                throw new UnableToReadBooksException(e.getMessage(), e);
            }
            if (i == 1) {
                String response;
                try {
                    response = mvcResult.getResponse().getContentAsString();
                } catch (UnsupportedEncodingException e) {
                    throw new UnableToReadBooksException(e.getMessage(), e);
                }
                assertTrue(response.contains(ExceptionMessage.THIS_BOOK_ALREADY_RESERVE.toString()));
            } else {
                int status = mvcResult.getResponse().getStatus();
                assertEquals(200, status);
            }
            i++;
        }
    }

    @Test
    void reserveBookDifferentUsers() throws Exception {
        MvcResult mvcResult;
        int i = 0;
        while (i <= copy) {
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(urlReserving)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(BookControllerTest.convertMapToJson(nameBook, Integer.toString(i))))
                    .andReturn();
            if (i == copy) {
                String response;
                response = mvcResult.getResponse().getContentAsString();
                assertTrue(response.contains(ExceptionMessage.NOT_COPIES.toString()));
            } else {
                int status = mvcResult.getResponse().getStatus();
                assertEquals(200, status);
            }
            i++;
        }
    }
}