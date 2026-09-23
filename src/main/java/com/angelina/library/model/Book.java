package com.angelina.library.model;

import jakarta.persistence.*;

/*
*   ENTITY means - this class is a table. table will be called book
* */

@Entity
public class Book {

    @Id         // means this field is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String authors; // comma-separated list, e.g. "Terry Pratchett,Neil Gaiman". Remove `author` once all rows are migrated.
    private int year;

    private Double supplierCost;

    public Book() {}

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public Double getSupplierCost() { return supplierCost; }
    public void setSupplierCost(Double supplierCost) { this.supplierCost = supplierCost; }
    public String getAuthors() { return authors; }
    public void setAuthors(String authors) { this.authors = authors; }
}
