package com.joelio.libraryapi.service;

import com.joelio.libraryapi.model.Book;

import java.util.Optional;

public interface BookService {
    Book save(Book any);
    Optional<Book> findByid(Long id);
}
