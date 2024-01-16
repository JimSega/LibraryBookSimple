package com.example.interview.excteption;

public class WrongTokenException extends RuntimeException {
    public WrongTokenException() {
    }

    public WrongTokenException(String message) {
        super(message);
    }

    public WrongTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
