package com.example.interview.controller;

import com.example.interview.DTO.BookDTO;
import com.example.interview.DTO.BookEntity;
import com.example.interview.DTO.Token;
import com.example.interview.excteption.ExceptionMessage;
import com.example.interview.excteption.NotFoundBookException;
import com.example.interview.excteption.SecondReserveThisBookException;
import com.example.interview.excteption.UserNameException;
import com.example.interview.model.Book;
import com.example.interview.repository.BookRepository;
import com.example.interview.service.Library;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor

public class BookController {

    private final BookRepository bookRepository;
    private final Library library;

    @GetMapping("/list")
    public List<Book> getList() {
        return bookRepository.getAll();
    }

    @PostMapping("/reserving")
    public synchronized Map<String, UUID> reserve(@RequestBody BookDTO bookDTO) {
        if (bookDTO.getUserName() == null) {
            throw new UserNameException(ExceptionMessage.NOT_FOND_USERNAME.toString());
        }
        BookEntity bookEntity = library.reserveBook(bookDTO.getName())
                .orElseThrow(NotFoundBookException::new);
        bookDTO.setName(bookEntity.getName());
        if(library.getMapBookUsedUser().containsValue(bookDTO)) {
            UUID uuidGotAlready = library.getMapBookUsedUser().entrySet().stream()
                    .filter(entry -> entry.getValue().equals(bookDTO))
                    .map(Map.Entry::getKey)
                    .toList()
                    .get(0);
            throw new SecondReserveThisBookException(ExceptionMessage.THIS_BOOK_ALREADY_RESERVE.toString(), uuidGotAlready);
        }
        UUID token = UUID.randomUUID();
        library.libraryUpdateBook(bookEntity, -1);
        library.getMapUUID().put(token, bookEntity);
        library.getMapBookUsedUser().put(token, bookDTO);
        return Map.of(bookDTO.getName(), token);
    }

    @GetMapping("/available")
    public List<String> getAvailable() {
        return library.getListBookNow().stream().filter(b -> b.getCopies() > 0).map(BookEntity::getName).toList();
    }

    @GetMapping(value = "/usedbook/{user}")
    public List<String> getUsedBook(@PathVariable("user") String userName) {
        return library.getBookUsedUser(userName);
    }

    @PostMapping("/return")
    public Map<String, String> returnBook(@RequestBody Token token) {
        BookDTO bookDTO = library.returnBook(token);
        return Map.of("return", bookDTO.getName());
    }
}
