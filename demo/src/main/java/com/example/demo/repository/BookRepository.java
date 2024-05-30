package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByPublished(boolean published);

    List<Book> findByTitleContaining(String title);
}