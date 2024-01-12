package com.example.interview.excteption;

public class MoreBooksException extends RuntimeException {
    public MoreBooksException() {
    }

    public MoreBooksException(String message) {
        super(message);
    }

    public MoreBooksException(String message, Throwable cause) {
        super(message, cause);
    }
}
