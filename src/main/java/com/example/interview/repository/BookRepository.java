package com.example.interview.repository;

import com.example.interview.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getAll();
}
