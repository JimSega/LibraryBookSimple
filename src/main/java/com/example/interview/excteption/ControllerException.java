package com.example.interview.excteption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerException {
    @ExceptionHandler(MoreBooksException.class)
    public ResponseEntity<Map<String, String>> moreBooksException() {
        return ResponseEntity.badRequest().body(Map.of("exception", ExceptionMessage.LOOT_OF_BOOKS.toString()));
    }

    @ExceptionHandler(NotCopiesException.class)
    public ResponseEntity<Map<String, String>> notFoundCopies() {
        return ResponseEntity.badRequest().body(Map.of("exception", ExceptionMessage.NOT_COPIES.toString()));
    }
}
