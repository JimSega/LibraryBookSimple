package com.example.interview.excteption;

public class NotCopiesException extends RuntimeException {
    public NotCopiesException() {
    }

    public NotCopiesException(String message) {
        super(message);
    }

    public NotCopiesException(String message, Throwable cause) {
        super(message, cause);
    }
}
