package com.example.interview.DTO;

import lombok.Data;

@Data
public class BookDTO {
    String name;
    String userName;

    @Override
    public String toString() {
        return name;
    }
}
