package com.joelio.libraryapi.service;

import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.repository.BookRepository;
import com.joelio.libraryapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService bookService;
    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setUp(){
        this.bookService = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest(){
        //cenario
        Book book = new Book(1l, "as aventuras", "joelio","123456");
        Mockito.when(repository.save(book)).thenReturn(book);

        //execução
        Book saveBook = bookService.save(book);

        //verificação
        assertThat(saveBook.getId()).isNotNull();
        assertThat(saveBook.getIsbn()).isEqualTo("123456");
        assertThat(saveBook.getAuthor()).isEqualTo("joelio");
        assertThat(saveBook.getTitle()).isEqualTo("as aventuras");
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficiente para criação do livro")
    public void CreateInvalidBookTest() throws Exception{

    }
}
