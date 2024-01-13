package com.example.interview.service;

import com.example.interview.model.Book;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class Library {
    private final Map<UUID, Book> mapUUID = new ConcurrentHashMap<>();

    public Map<UUID, Book> getMapUUID() {
        return mapUUID;
    }
}
