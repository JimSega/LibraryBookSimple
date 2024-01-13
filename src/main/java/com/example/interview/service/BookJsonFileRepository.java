package com.example.interview.service;

import com.example.interview.excteption.*;
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

    public Optional<Book> reserveBook(String name) {
        List<Book> searchingBook;
        if (name != null) {
            searchingBook = listBook.stream().filter(book -> book
                            .getName()
                            .toLowerCase()
                            .contains(name.toLowerCase()))
                    .toList();
        } else throw new NotFoundFieldNameBookException(ExceptionMessage.NOT_FOUND_FIELD_NAME_BOOK.toString());
        if (searchingBook.size() == 0) {
            throw new NotFoundBookException(ExceptionMessage.NOT_FOUND_BOOK.toString());
        }
        if (searchingBook.size() > 1) {
            throw new LotOfBooksException(ExceptionMessage.LOOT_OF_BOOKS.toString());
        } else {
            searchingBook = searchingBook.stream().filter(book -> book
                            .getCopies() > 0)
                    .toList();
            if (searchingBook.size() == 1) {
                return searchingBook.stream().findFirst();
            } else throw new NotCopiesException(ExceptionMessage.NOT_COPIES + name);
        }
    }
}
