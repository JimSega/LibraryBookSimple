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

    public synchronized UUID reserveBook(BookDTO bookDTO) {
        if (bookDTO.getUserName() == null) {
            throw new UserNameException(ExceptionMessage.NOT_FOND_USERNAME.toString());
        }

        List<BookEntity> searchingBook;
        Optional<BookEntity> optionalBookEntity;
        if (bookDTO.getName() != null) {
            searchingBook = listBookNow.stream().filter(book -> book
                            .getName()
                            .toLowerCase()
                            .contains(bookDTO.getName().toLowerCase()))
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
                optionalBookEntity = searchingBook.stream().findFirst();
            } else throw new NotCopiesException(ExceptionMessage.NOT_COPIES + bookDTO.getName());
        }
        BookEntity bookEntity = optionalBookEntity.orElseThrow(NotFoundBookException::new);
        bookDTO.setName(bookEntity.getName());
        if (mapBookUsedUser.containsValue(bookDTO)) {
            UUID uuidGotAlready = mapBookUsedUser.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(bookDTO))
                    .map(Map.Entry::getKey)
                    .toList()
                    .get(0);
            throw new SecondReserveThisBookException(ExceptionMessage.THIS_BOOK_ALREADY_RESERVE.toString(), uuidGotAlready);
        }
        UUID token = UUID.randomUUID();
        libraryUpdateBook(bookEntity, -1);
        mapUUID.put(token, bookEntity);
        mapBookUsedUser.put(token, bookDTO);
        return token;
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
