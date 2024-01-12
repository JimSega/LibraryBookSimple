package com.example.interview.service;

import com.example.interview.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAll();
}
