package com.joelio.libraryapi.service.impl;

import com.joelio.libraryapi.exception.BusinessException;
import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.repository.BookRepository;
import com.joelio.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository){
        this.repository = repository;
    }
    @Override
    public Book save(Book book) {
        if(repository.existsByIsbn(book.getIsbn())){
            throw new BusinessException("Isbn ja cadastrado");
        }
        return repository.save(book);
    }
    @Override
    public Optional getById(Long id) {
        return Optional.empty();
    }
}
