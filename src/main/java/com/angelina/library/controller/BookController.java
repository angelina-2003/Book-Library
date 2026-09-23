package com.angelina.library.controller;

import com.angelina.library.model.Book;
import com.angelina.library.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Every method just hands over the task to the service.

@RestController
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/books")
    public List<Book> all() {
        return service.findAll();
    }

    @GetMapping("/books/{id}")
    public Book one(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/books")
    public Book create(@RequestBody Book book) {
        return service.create(book);
    }


}
