package com.angelina.library.controller;

import com.angelina.library.dto.BookDTO;
import com.angelina.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3003")
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
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO book) {
        book.normaliseAuthors();
        BookDTO created = service.create(book);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id)
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/books/{id}")
    public BookDTO update(@PathVariable Long id, @RequestBody BookDTO book) {
        book.normaliseAuthors();
        return service.update(id, book);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/books")
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }
}