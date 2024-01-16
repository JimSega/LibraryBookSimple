package com.example.interview.excteption;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessage {
    LOOT_OF_BOOKS("library has got a loot of books with this name"),
    NOT_COPIES("not copies this book, try later"),
    NOT_FOUND_FIELD_NAME_BOOK("you should use field Name"),
    NOT_FOUND_BOOK("library hasn't these book"),
    WRONG_TOKEN("this token is wrong"),
    WRONG_FORMAT("please use json format");
    private final String exception;
    @Override
    public String toString() {
        return this.exception;
    }
}
