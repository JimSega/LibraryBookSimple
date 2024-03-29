package com.example.interview.excteption;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.UUID;

@ControllerAdvice
public class ControllerException {
    @ExceptionHandler(LotOfBooksException.class)
    public ResponseEntity<Map<String, String>> moreBooksException() {
        return ResponseEntity.badRequest().body(Map.of("exception", ExceptionMessage.LOOT_OF_BOOKS.toString()));
    }

    @ExceptionHandler(NotCopiesException.class)
    public ResponseEntity<Map<String, String>> notFoundCopies() {
        return ResponseEntity.badRequest().body(Map.of("exception", ExceptionMessage.NOT_COPIES.toString()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> wrongFormat() {
        return ResponseEntity.badRequest().body(Map.of("exception", ExceptionMessage.WRONG_FORMAT.toString()));
    }

    @ExceptionHandler(NotFoundBookException.class)
    public ResponseEntity<Map<String, String>> notFoundBook() {
        return ResponseEntity.badRequest().body(Map.of("exception", ExceptionMessage.NOT_FOUND_BOOK.toString()));
    }

    @ExceptionHandler(NotFoundFieldNameBookException.class)
    public ResponseEntity<Map<String, String>> notFoundField() {
        return ResponseEntity.badRequest().body(Map.of("exception", ExceptionMessage.NOT_FOUND_FIELD_NAME_BOOK
                .toString()));
    }

    @ExceptionHandler(WrongTokenException.class)
    public ResponseEntity<Map<String, String>> wrongToken() {
        return ResponseEntity.badRequest().body(Map.of("exception", ExceptionMessage.WRONG_TOKEN.toString()));
    }

    @ExceptionHandler(UserNameException.class)
    public ResponseEntity<Map<String, String>> notFoundUserName() {
        return ResponseEntity.badRequest().body(Map.of("exception", ExceptionMessage.NOT_FOND_USERNAME.toString()));
    }

    @ExceptionHandler(SecondReserveThisBookException.class)
    public ResponseEntity<Map<UUID, String>> secondReserve(SecondReserveThisBookException ex) {
        return ResponseEntity.badRequest().body(Map.of(ex.getUuid(),
                ExceptionMessage.THIS_BOOK_ALREADY_RESERVE.toString()));
    }

}
