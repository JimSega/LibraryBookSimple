package com.example.interview.repository;

import com.example.interview.excteption.*;
import com.example.interview.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class BookJsonFileRepository implements BookRepository {

    private final List<Book> listBook;

    public BookJsonFileRepository(@Value("${book.file.name}") String fileName, ObjectMapper objectMapper) {
        try {
            this.listBook = objectMapper.readValue(this.getClass().getClassLoader().getResourceAsStream(fileName),
                    new TypeReference<>() {
                    });
        } catch (IOException e) {
            throw new UnableToReadBooksException("Json" + fileName + "isn't read", e);
        }
    }

    public List<Book> getAll() {
        return listBook;
    }
}
