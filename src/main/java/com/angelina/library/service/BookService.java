package com.angelina.library.service;

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

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Book findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Book create(Book book) {
        return repository.save(book);
    }
}