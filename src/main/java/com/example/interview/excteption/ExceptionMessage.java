package com.example.interview.excteption;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessage {
    LOOT_OF_BOOKS("a loot of books"),
    NOT_COPIES("not copies this book, try later");
    private final String exception;
    @Override
    public String toString() {
        return this.exception;
    }
}
