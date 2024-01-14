package com.example.interview.model;

import com.example.interview.repository.BookRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Data
public class Library {

    private final Map<UUID, Book> mapUUID = new ConcurrentHashMap<>();

    private volatile List<Book> listBookNow;

    Library(BookRepository bookRepository) {
        this.listBookNow = bookRepository.getAll();
    }

    public void libraryUpdateBook(Book book, int i) {
        listBookNow = listBookNow.stream().peek(b -> {
            if (b.equals(book)) {
                b.setCopies(book.getCopies() + i);
            }
        }).collect(Collectors.toList());
    }
}
