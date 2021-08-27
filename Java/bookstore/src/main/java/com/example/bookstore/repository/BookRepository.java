package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.model.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	List<Book> findByTitle(String title);
	// List<Book> findByAuthors(String authorName);
}
