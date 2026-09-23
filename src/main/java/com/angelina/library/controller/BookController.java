package com.angelina.library.controller;

import com.angelina.library.dto.BookDTO;

import com.angelina.library.model.Book;
import com.angelina.library.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Every method just hands over the task to the service.

@CrossOrigin(origins = "http://localhost:3002")
@RestController
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/books")
    public List<BookDTO> all() {
        return service.findAll();
    }

    @GetMapping("/books/{id}")
    public BookDTO one(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/books")
    public BookDTO create(@RequestBody BookDTO book) {
        return service.create(book);
    }


}
