package com.example.interview.controller;

import com.example.interview.DTO.BookDTO;
import com.example.interview.excteption.NotFoundBookException;
import com.example.interview.model.Book;
import com.example.interview.service.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor

public class BookController {

    private final BookRepository bookRepository;

    @GetMapping("/list")
    public List<Book> getList() {
        return bookRepository.getAll();
    }
    @PostMapping("/reserving")
    public Book reserve(@RequestBody BookDTO bookDTO) {
        return bookRepository.reserveBook(bookDTO.name()).orElseThrow(NotFoundBookException::new);
    }
}
