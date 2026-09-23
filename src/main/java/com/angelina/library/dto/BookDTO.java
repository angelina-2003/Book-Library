package com.angelina.library.dto;

import com.angelina.library.model.Book;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookDTO {
    public Long id;
    public String title;
    public String author;
    public List<String> authors; // NEW shape
    public int year;

    public String label;

    public BookDTO() {}

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.year = book.getYear();
        this.label = book.getTitle() + " (" + book.getYear() + ")";

        if (book.getAuthors() != null && !book.getAuthors().isBlank()) {
            // NEW row: build `author` from `authors` so OLD clients still find it
            this.authors = Arrays.asList(book.getAuthors().split(","));
            this.author = String.join(", ", this.authors);
        } else {
            // OLD row: build `authors` from `author` so NEW clients get a list
            this.author = book.getAuthor();
            this.authors = this.author == null ? List.of() : List.of(this.author);
        }

    }

    // Old-shape request -> new shape. Called by the controller before the
    // service ever sees this DTO.
    public void normaliseAuthors() {
        if ((authors == null || authors.isEmpty()) && author != null && !author.isBlank()) {
            authors = List.of(author);
        }
    }

    public String authorsAsColumn() {
        if (authors == null) return null;
        return authors.stream().map(String::trim).collect(Collectors.joining(","));
    }
}
