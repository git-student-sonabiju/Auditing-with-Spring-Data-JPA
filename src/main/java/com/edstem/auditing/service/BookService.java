package com.edstem.auditing.service;

import com.edstem.auditing.dto.BookDTO;
import com.edstem.auditing.model.Book;
import com.edstem.auditing.repository.BookRepository;
import com.edstem.auditing.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

	private final BookRepository bookRepository;

	public List<BookDTO> getAll() {
		return bookRepository.findAll().stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}

	public BookDTO getBookById(Long id) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found."));
		return toDTO(book);
	}

	public BookDTO create(BookDTO bookDTO) {
		Book book = toEntity(bookDTO);
		Book savedBook = bookRepository.save(book);
		return toDTO(savedBook);
	}

	public BookDTO update(Long id, BookDTO bookDTO) {
		Book existing = bookRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Book with id " + id + " not found."));

		existing.setTitle(bookDTO.getTitle());
		existing.setIsbn(bookDTO.getIsbn());
		existing.setAuthor(bookDTO.getAuthor());
		existing.setPrice(bookDTO.getPrice());

		Book updatedBook = bookRepository.save(existing);
		return toDTO(updatedBook);
	}

	public void delete(Long id) {
		if (!bookRepository.existsById(id)) {
			throw new EntityNotFoundException("Book with id " + id + " not found.");
		}
		bookRepository.deleteById(id);
	}

	private BookDTO toDTO(Book book) {
		return BookDTO.builder()
				.id(book.getId())
				.title(book.getTitle())
				.isbn(book.getIsbn())
				.author(book.getAuthor())
				.price(book.getPrice())
				.createdBy(book.getCreatedBy())
				.createdDate(book.getCreatedDate())
				.lastModifiedBy(book.getLastModifiedBy())
				.lastModifiedDate(book.getLastModifiedDate())
				.build();
	}

	private Book toEntity(BookDTO dto) {
		Book book = new Book();
		book.setTitle(dto.getTitle());
		book.setIsbn(dto.getIsbn());
		book.setAuthor(dto.getAuthor());
		book.setPrice(dto.getPrice());
		return book;
	}
}
