package com.angelina.library.service;

import com.angelina.library.dto.BookDTO;
import com.angelina.library.errors.BookNotFoundException;
import com.angelina.library.errors.InvalidBookException;
import com.angelina.library.model.Book;
import com.angelina.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookDTO> findAll() {
        return repository.findAll().stream().map(BookDTO::new).toList();
    }

    public BookDTO findById(Long id) {
        return repository.findById(id)
                .map(BookDTO::new)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public BookDTO create(BookDTO dto) {
        validate(dto);
        Book book = new Book();
        book.setTitle(dto.title);
        book.setYear(dto.year);
        book.setAuthors(dto.authorsAsColumn());
        return new BookDTO(repository.save(book));
    }

    public BookDTO update(Long id, BookDTO dto) {
        validate(dto);
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        book.setTitle(dto.title);
        book.setYear(dto.year);
        book.setAuthors(dto.authorsAsColumn());
        return new BookDTO(repository.save(book));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    private void validate(BookDTO dto) {
        if (dto.title == null || dto.title.isBlank()) {
            throw new InvalidBookException("Book title is required");
        }
    }
}