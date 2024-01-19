package com.example.interview.service;

import com.example.interview.DTO.BookDTO;
import com.example.interview.DTO.BookEntity;
import com.example.interview.DTO.BookMapper;
import com.example.interview.DTO.Token;
import com.example.interview.excteption.*;
import com.example.interview.repository.BookRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Data
public class Library {

    private final Map<UUID, BookEntity> mapUUID = new ConcurrentHashMap<>();
    private volatile List<BookEntity> listBookNow;
    private Map<UUID, BookDTO> mapBookUsedUser = new ConcurrentHashMap<>();

    Library(BookRepository bookRepository) {
        bookRepository.getAll().stream().map(BookMapper.INSTANCE::bookToBookDTO).map(BookDTO::getName).forEach(System.out::println);
        this.listBookNow = bookRepository.getAll().stream()
                .map(BookMapper.INSTANCE::bookToBookEntity).collect(Collectors.toList());
        listBookNow.stream().map(BookEntity::getName).forEach(System.out::println);
    }

    public synchronized void libraryUpdateBook(BookEntity bookEntity, int i) {
        listBookNow = listBookNow.stream().peek(b -> {
            if (b.getName().equals(bookEntity.getName())) {
                b.setCopies(b.getCopies() + i);
            }
        }).collect(Collectors.toList());
    }

    public Optional<BookEntity> reserveBook(String name) {
        List<BookEntity> searchingBook;
        if (name != null) {
            listBookNow.stream().map(BookEntity::getName).forEach(System.out::println);
            searchingBook = listBookNow.stream().filter(book -> book
                            .getName()
                            .toLowerCase()
                            .contains(name.toLowerCase()))
                    .toList();
        } else throw new NotFoundFieldNameBookException(ExceptionMessage.NOT_FOUND_FIELD_NAME_BOOK.toString());
        if (searchingBook.isEmpty()) {
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

    public List<String> getBookUsedUser(String userName) {
        return mapBookUsedUser.values().stream()
                .filter(bookDTO -> bookDTO.getUserName().equals(userName))
                .map(BookDTO::getName)
                .collect(Collectors.toList());
    }

    public BookDTO returnBook(Token token) {
        BookEntity bookEntity = mapUUID.remove(token.token());
        if (bookEntity != null) {
            libraryUpdateBook(bookEntity, 1);
            return mapBookUsedUser.remove(token.token());
        } else throw new WrongTokenException(ExceptionMessage.WRONG_TOKEN.toString());
    }
}
