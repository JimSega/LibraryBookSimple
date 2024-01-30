package com.example.interview.controller;

import com.example.interview.DTO.BookDTO;
import com.example.interview.DTO.BookEntity;
import com.example.interview.DTO.Token;
import com.example.interview.service.Library;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class ReserveController {

    private final Library library;

    @PostMapping("/reserving")
    public synchronized Map<String, UUID> reserve(@RequestBody BookDTO bookDTO) {
        return Map.of(bookDTO.getName(), library.reserveBook(bookDTO));
    }

    @GetMapping("/available")
    public List<String> getAvailable() {
        return library.getListBookNow().stream().filter(b -> b.getCopies() > 0).map(BookEntity::getName).toList();
    }

    @GetMapping(value = "/usedbook/{user}")
    public List<String> getUsedBook(@PathVariable("user") String userName) {
        return library.getBookUsedUser(userName);
    }

    @PostMapping("/return")
    public Map<String, String> returnBook(@RequestBody Token token) {
        return Map.of("return", library.returnBook(token).getName());
    }
}
