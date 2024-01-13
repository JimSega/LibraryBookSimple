package com.example.interview.DTO;


import com.example.interview.model.Book;

public record BookDTO(String name) {

    public static BookDTO convertBookToDTO(Book book) {
        BookDTO bookDTO = new BookDTO(book.getName());
        return bookDTO;
    }

    @Override
    public String toString() {
        return name;
    }
}
