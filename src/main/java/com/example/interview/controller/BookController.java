package com.example.interview.controller;

import com.example.interview.model.Book;
import com.example.interview.service.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
