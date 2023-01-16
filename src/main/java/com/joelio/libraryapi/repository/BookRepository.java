package com.joelio.libraryapi.repository;

import com.joelio.libraryapi.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {

    boolean existsByIsbn(String isbn);
}
