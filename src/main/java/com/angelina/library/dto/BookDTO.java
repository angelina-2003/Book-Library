package com.angelina.library.dto;

import com.angelina.library.model.Book;

public class BookDTO {
    public Long id;
    public String title;
    public String author;
    public int year;

    public String label;

    public BookDTO() {}

    public BookDTO(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.year = book.getYear();
        this.label = book.getTitle() + " (" + book.getYear() + ")";
    }
}
