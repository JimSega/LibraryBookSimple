package com.example.interview.excteption;

public class NotFoundFieldNameBookException extends RuntimeException{
    public NotFoundFieldNameBookException() {
    }

    public NotFoundFieldNameBookException(String message) {
        super(message);
    }

    public NotFoundFieldNameBookException(String message, Throwable cause) {
        super(message, cause);
    }
}
