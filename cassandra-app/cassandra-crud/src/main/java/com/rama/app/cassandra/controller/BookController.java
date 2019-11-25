package com.rama.app.cassandra.controller;

import com.rama.app.cassandra.repository.Book;
import com.rama.app.cassandra.repository.BookRepository;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/cassandra/book")
public class BookController {

	private final BookRepository bookRepository;

	public BookController(final BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public Book createBookdata(@Valid @RequestBody Book book) {
		Book insertData=bookRepository.insert(book);
		return insertData;
	}
	
	@RequestMapping(value = "/read/{isbn}", method = RequestMethod.GET)
	@ResponseBody
	public Optional<Book> userName(@PathVariable("isbn")String  isbn) {
		return bookRepository.findByIsbn(isbn);

	}
}

