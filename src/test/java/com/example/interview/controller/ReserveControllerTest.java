package com.example.interview.controller;

import com.example.interview.DTO.BookMapper;
import com.example.interview.InterviewTaskApplication;
import com.example.interview.excteption.ExceptionMessage;
import com.example.interview.excteption.UnableToReadBooksException;
import com.example.interview.repository.BookRepository;
import com.example.interview.service.Library;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = InterviewTaskApplication.class)
@TestPropertySource("classpath:test.properties")
class ReserveControllerTest {

    private MockMvc mockMvc;

    @Autowired
    Library library;

    @Autowired
    BookRepository bookRepository;
    @Value("${test.url.books.reserving}")
    String urlReserving;

    @Value("${test.reserving.copy}")
    int copy;
    @Value("${test.reserving.bookdto.json}")
    String reserving;
    @Value("${test.reserving.name.book}")
    String nameBook;

    @Value("${test.url.books.available}")
    String available;

    @Value("${test.url.books.usedbook}")
    String usedBook;

    @Value("${test.user.name}")
    String userName;
    @Value("${test.url.books.return}")
    String returnBook;

    @BeforeEach
    protected void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void restartLibrary() {
        library.setListBookNow(bookRepository.getAll().stream()
                .map(BookMapper.INSTANCE::bookToBookEntity).collect(Collectors.toList()));
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

    @Test
    void getAvailableBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(available)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertNotEquals("[]", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getUsedBookTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(usedBook, userName)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals("[]", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void firstReserveBookThenCheckUsedBookThenReturnBook() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(urlReserving)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(reserving))
                .andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Pattern pattern = Pattern.compile("[\\w-]{36}");
        Matcher matcher = pattern.matcher(response);
        String token = null;
        if (matcher.find()) {
            token = matcher.group();
        }
        assertNotNull(token);
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(usedBook, userName)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertNotEquals("[]", mvcResult.getResponse().getContentAsString());
        mockMvc.perform(MockMvcRequestBuilders.post(returnBook)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\"token\": \"" + token + "\"}"))
                .andReturn();
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(usedBook, userName)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        assertEquals("[]", mvcResult.getResponse().getContentAsString());
    }

}