package com.joelio.libraryapi.service.impl;

import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.repository.BookRepository;
import com.joelio.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository){
        this.repository = repository;
    }
    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
}
