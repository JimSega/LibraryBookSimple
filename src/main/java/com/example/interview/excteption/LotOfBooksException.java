package com.example.interview.excteption;

public class LotOfBooksException extends RuntimeException {
    public LotOfBooksException() {
    }

    public LotOfBooksException(String message) {
        super(message);
    }

    public LotOfBooksException(String message, Throwable cause) {
        super(message, cause);
    }
}
