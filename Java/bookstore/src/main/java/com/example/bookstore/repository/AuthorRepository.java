package com.example.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bookstore.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
	List<Author> findByAuthorName(String authorName);
}
