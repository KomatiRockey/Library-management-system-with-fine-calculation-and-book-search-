package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findFirstByTitleIgnoreCase(String title);
    List<Book> findByTitleContainingIgnoreCase(String title);
}
