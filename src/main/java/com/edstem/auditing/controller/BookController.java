package com.edstem.auditing.controller;

import com.edstem.auditing.dto.BookDTO;
import com.edstem.auditing.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public List<BookDTO> getAll() {
		return bookService.getAll();
	}

	@GetMapping("/{id}")
	public BookDTO getBookById(@PathVariable Long id) {
		return bookService.getBookById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BookDTO create(@RequestBody BookDTO bookDTO) {
		return bookService.create(bookDTO);
	}

	@PutMapping("/{id}")
	public BookDTO update(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
		return bookService.update(id, bookDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		bookService.delete(id);
	}
}
