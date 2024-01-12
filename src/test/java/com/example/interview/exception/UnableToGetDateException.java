package com.example.interview.exception;

public class UnableToGetDateException extends RuntimeException {
    public UnableToGetDateException() {
    }

    public UnableToGetDateException(String message) {
        super(message);
    }

    public UnableToGetDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
