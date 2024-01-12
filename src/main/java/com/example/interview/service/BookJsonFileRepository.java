package com.example.interview.service;

import com.example.interview.excteption.MoreBooksException;
import com.example.interview.excteption.UnableToReadBooksException;
import com.example.interview.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class BookJsonFileRepository implements BookRepository {
    private final List<Book> listBook;

    public BookJsonFileRepository(@Value("${book.file.name}") String fileName, ObjectMapper objectMapper) {
        try {
            this.listBook = objectMapper.readValue(this.getClass().getClassLoader().getResourceAsStream(fileName), new TypeReference<List<Book>>() {
            });
        } catch (IOException e) {
            throw new UnableToReadBooksException("Json" + fileName + "isn't read", e);
        }
    }

    public List<Book> getAll() {
        return listBook;
    }

    public Optional<Book> reserveBook(String name) {
        Stream<Book> reserveBook = listBook.stream().filter(book -> book
                .getName()
                .toLowerCase()
                .contains(name.toLowerCase()));
        if (reserveBook.count() > 1) {
            throw new MoreBooksException("Library has got more one book with this name!");
        } else return reserveBook.findFirst();

    }
}
