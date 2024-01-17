package com.example.interview.excteption;

import lombok.Getter;

import java.util.UUID;

@Getter
public class SecondReserveThisBookException extends RuntimeException {
    private UUID uuid;

    public SecondReserveThisBookException() {
    }

    public SecondReserveThisBookException(String message) {
        super(message);
    }

    public SecondReserveThisBookException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecondReserveThisBookException(String message, UUID uuuid) {
        super(message);
        this.uuid = uuuid;
    }
}
