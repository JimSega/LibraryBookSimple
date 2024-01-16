package com.example.interview.DTO;

public record BookDTO(String name, String userName) {

    @Override
    public String toString() {
        return name;
    }
}
