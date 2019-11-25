package com.rama.app.cassandra.repository;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("book")
public class Book {

    @PrimaryKeyColumn(name = "isbn", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String isbn;
    @Column
    private String price;

    @Column("author")
    private String writer;

    @Column
    private String publisher;

    public Book(String isbn, String price, String writer, String publisher) {
        this.isbn = isbn;
        this.price = price;
        this.writer = writer;
        this.publisher = publisher;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(final String price) {
        this.price = price;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(final String writer) {
        this.writer = writer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(final String publisher) {
        this.publisher = publisher;
    }




}
