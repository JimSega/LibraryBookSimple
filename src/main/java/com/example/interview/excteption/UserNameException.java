package com.example.interview.excteption;

public class UserNameException extends RuntimeException{
    public UserNameException() {
    }

    public UserNameException(String message) {
        super(message);
    }

    public UserNameException(String message, Throwable cause) {
        super(message, cause);
    }

}
