package com.example.interview.controller;

import com.example.interview.InterviewTaskApplication;
import com.example.interview.exception.UnableToGetDateException;
import com.example.interview.excteption.UnableToReadBooksException;
import com.example.interview.model.Book;
import com.example.interview.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @MockBean
    private BookRepository bookRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Test
    void getListBookTest() throws Exception {
        // given
        when(bookRepository.getAll()).thenReturn(List.of(new Book(1, "Author", 2, "Book")));

        // when
        mockMvc.perform(get("/books/list")
                .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"author\":\"Author\",\"copies\":2,\"name\":\"Book\"}]"));
    }
}