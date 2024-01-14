package com.example.interview.DTO;

public record BookDTO(String name) {

    @Override
    public String toString() {
        return name;
    }
}
