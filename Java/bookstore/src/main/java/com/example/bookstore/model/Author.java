package com.example.bookstore.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Author {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int authorId;
	private String authorName;
	@ManyToMany(mappedBy="authors")
	@JsonIgnoreProperties("authors")
	private List<Book> books;
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthor_name(String authorName) {
		this.authorName = authorName;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBook(List<Book> books) {
		this.books = books;
	}
}
