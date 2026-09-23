package com.angelina.library.service;

import com.angelina.library.dto.BookDTO;
import com.angelina.library.errors.BookNotFoundException;

import com.angelina.library.errors.BookNotFoundException;
import com.angelina.library.model.Book;
import com.angelina.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import com.angelina.library.errors.InvalidBookException;

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
        return repository.findById(id).map(BookDTO::new).orElseThrow(()-> new BookNotFoundException(id));
    }

    public BookDTO create(BookDTO dto) {
        if (dto.title == null || dto.title.isBlank()) {
            throw new InvalidBookException("Book title is required");
        }
        Book book = new Book(dto.title, dto.author, dto.year);
        Book saved = repository.save(book);
        return new BookDTO(saved);
    }

}