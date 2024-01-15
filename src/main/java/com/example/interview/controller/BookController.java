package com.example.interview.controller;

import com.example.interview.DTO.BookDTO;
import com.example.interview.DTO.BookMapper;
import com.example.interview.excteption.NotFoundBookException;
import com.example.interview.model.Book;
import com.example.interview.repository.BookRepository;
import com.example.interview.model.Library;
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
    public synchronized Map<BookDTO, UUID> reserve(@RequestBody BookDTO bookDTO) {
        Book book = bookRepository.reserveBook(library.getListBookNow(), bookDTO.name())
                .orElseThrow(NotFoundBookException::new);
        UUID token = UUID.randomUUID();
        library.libraryUpdateBook(book, -1);
        library.getMapUUID().put(token, book);
        return Map.of(BookMapper.BookMapperDTO.bookToBookDTO(book), token);
    }

    @GetMapping("/available")
    public List<String> getAvailable() {
        return library.getListBookNow().stream().filter(b -> b.getCopies() > 0).map(Book::getName).toList();
    }
}
