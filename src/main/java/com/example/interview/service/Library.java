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
        this.listBookNow = bookRepository.getAll().stream()
                .map(BookMapper.INSTANCE::bookToBookEntity).collect(Collectors.toList());
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
                .filter(bookDTO -> bookDTO.userName().equals(userName))
                .map(BookDTO::name)
                .collect(Collectors.toList());
    }

    public void returnBook(Token token) {
        BookDTO bookDTO = mapBookUsedUser.remove(token.token());
        if (bookDTO != null) {
            listBookNow.stream().peek(b -> {
                if(b.getName().equals(bookDTO.name())) {

                }
            })
        } else throw new WrongTokenException(ExceptionMessage.WRONG_TOKEN.toString());

    }
}
