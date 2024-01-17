package com.example.interview.repository;

import com.example.interview.InterviewTaskApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = InterviewTaskApplication.class)
class BookJsonFileRepositoryTest {
    BookJsonFileRepository bookJsonFileRepository;

    protected BookJsonFileRepositoryTest(@Autowired BookJsonFileRepository bookJsonFileRepository) {
        this.bookJsonFileRepository = bookJsonFileRepository;
    }

    @Test
    void getAll() {
        assertNotNull(bookJsonFileRepository.getAll());
    }
}