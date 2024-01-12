package com.example.interview.excteption;

public class UnableToReadBooksException extends RuntimeException {
    public UnableToReadBooksException() {
    }

    public UnableToReadBooksException(String message) {
        super(message);
    }

    public UnableToReadBooksException(String message, Throwable cause) {
        super(message, cause);
    }
}
