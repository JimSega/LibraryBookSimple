package com.example.interview.service;

import com.example.interview.excteption.MoreBooksException;
import com.example.interview.excteption.NotCopiesException;
import com.example.interview.excteption.UnableToReadBooksException;
import com.example.interview.model.Book;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class BookJsonFileRepository implements BookRepository {
    private final List<Book> listBook;

    public BookJsonFileRepository(@Value("${book.file.name}") String fileName, ObjectMapper objectMapper) {
        try {
            this.listBook = objectMapper.readValue(this.getClass().getClassLoader().getResourceAsStream(fileName), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new UnableToReadBooksException("Json" + fileName + "isn't read", e);
        }
    }

    public List<Book> getAll() {
        return listBook;
    }

    public Optional<Book> reserveBook(String name) {
        List<Book> searchingBook = listBook.stream().filter(book -> book
                        .getName()
                        .toLowerCase()
                        .contains(name.toLowerCase()))
                .toList();
        if (searchingBook.size() > 1) {
            throw new MoreBooksException("Library has got a lot of books with this name!");
        } else {
            searchingBook = searchingBook.stream().filter(book -> book
                            .getCopies() > 0)
                    .toList();
            if (searchingBook.size() == 1) {
                return searchingBook.stream().findFirst();
            } else throw new NotCopiesException("Library hasn't copies this book now! " + name);
        }
    }
}
