package com.example.bookstore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.model.Author;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/")
public class BookController {

	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	AuthorRepository authorRepository;
	
	@PostMapping("/books")
	public ResponseEntity<?> addBook(@RequestBody Book book) {
		bookRepository.save(book);
		Map<String, String> body = new HashMap<>();
		body.put("result", "Book added successfully");
		return ResponseEntity.ok().body(body);
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable(value = "id") int isbn) {
		Optional<Book> employeeOpt = bookRepository.findById(isbn);
		Map<String, String> body = new HashMap<>();
		if (employeeOpt.isPresent()) {
			Book book = employeeOpt.get();
			bookRepository.delete(book);
			body.put("result", "Book deleted successfully");
			return ResponseEntity.ok().body(body);
		}
		else {
			body.put("result", "Book ISBN does not exist.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
		}
	}
	
	@PutMapping("/books/{id}")
	public ResponseEntity<?> updateBook(@PathVariable(value = "id") int isbn,
	         @RequestBody Book bookDetails) {
		Optional<Book> bookOpt = bookRepository.findById(isbn);
		Map<String, String> body = new HashMap<>();
		if (bookOpt.isPresent()) {
			Book book = bookOpt.get();
			//
			book.setTitle(bookDetails.getTitle());
			book.setAuthors(bookDetails.getAuthors());
			book.setYear(bookDetails.getYear());
			book.setPrice(bookDetails.getPrice());
	        bookRepository.save(book);
			body.put("result", "Book updated successfully");
			return ResponseEntity.ok().body(body);
		}
		body.put("result", "Book ISBN does not exist");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);      
	}
	
	// search book by title (exact match) or author (exact match)
	@GetMapping("/books")
	public List<Book> findBooks(@RequestParam("title") String title, @RequestParam("authorName") String authorName) {
		List<Book> booksFound = bookRepository.findByTitle(title);
		List<Author> authorsFound = authorRepository.findByAuthorName(authorName);
		int numAuthors = authorsFound.size();
		for (int i = 0; i < numAuthors; i++) {
			List<Book> booksByAuthor = authorsFound.get(i).getBooks();
			int numBooks = booksByAuthor.size();
			for (int j = 0; j < numBooks; j++) {
				Book book = booksByAuthor.get(j);
				if (!booksFound.contains(book)) {
					booksFound.add(book);
				}
			}
		}
		return booksFound;
	}
}
