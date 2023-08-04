package com.joelio.libraryapi.service;

import com.joelio.libraryapi.DTO.BookDTO;
import com.joelio.libraryapi.exception.BusinessException;
import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.repository.BookRepository;
import com.joelio.libraryapi.service.impl.BookServiceImpl;
import org.assertj.core.api.Assertions;
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
    public void setUp() {
        this.bookService = new BookServiceImpl(repository);
    }

    private Book createValidBook(){
        return new Book(1l, "as aventuras", "joelio","123456");
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest() {
        //cenario
        Book  book = Book.builder().author("joelio").title("as aventuras").isbn("123456").build();
        Mockito.when(repository.save(book)).thenReturn(Book.builder()
                                                            .id(1L)
                                                            .author("joelio")
                                                            .title("as aventuras")
                                                            .isbn("123456").build());

        //execução
        Book saveBook = bookService.save(book);

        //verificação
        assertThat(saveBook.getId()).isNotNull();
        assertThat(saveBook.getIsbn()).isEqualTo("123456");
        assertThat(saveBook.getAuthor()).isEqualTo("joelio");
        assertThat(saveBook.getTitle()).isEqualTo("as aventuras");
    }

    @Test
    @DisplayName("deve lançar erro de negocio ao tentar salvar livro com ISBN repetido")
    public void shouldNotSaveABookWithDuplicateISBN() throws Exception {
        //senario
        Book book = createValidBook();
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);

        // execução
        Throwable exception = Assertions.catchThrowable(()-> bookService.save(book));

        //verificação
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Isbn ja cadastrado");

        Mockito.verify(repository, Mockito.never()).save(book);
    }
}
