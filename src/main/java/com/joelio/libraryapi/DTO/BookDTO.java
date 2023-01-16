package com.joelio.libraryapi.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BookDTO {
    private Long id;
    @NotEmpty
    @NotNull
    private String title;
    @NotEmpty
    @NotNull
    private String author;
    @NotEmpty
    @NotNull
    private String isbn;

    public BookDTO() {
    }

    public BookDTO(Long id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
