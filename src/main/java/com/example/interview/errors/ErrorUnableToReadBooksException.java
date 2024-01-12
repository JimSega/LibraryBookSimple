package com.example.interview.errors;

public class ErrorUnableToReadBooksException extends RuntimeException {
    public ErrorUnableToReadBooksException() {
    }

    public ErrorUnableToReadBooksException(String message) {
        super(message);
    }

    public ErrorUnableToReadBooksException(String message, Throwable cause) {
        super(message, cause);
    }
}
