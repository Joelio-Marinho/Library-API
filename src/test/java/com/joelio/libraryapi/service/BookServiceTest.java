package com.joelio.libraryapi.service;
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
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
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
        Book  book = createValidBook();
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
        // execução para informar para o repository que o ISBN existe
        Mockito.when(repository.existsByIsbn(Mockito.anyString())).thenReturn(true);

        // execução
        Throwable exception = Assertions.catchThrowable(()-> bookService.save(book));

        //verificação
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Isbn ja cadastrado");

        Mockito.verify(repository, Mockito.never()).save(book);
    }
    @Test
    @DisplayName("Deve obter um livro por id")
    public void getByIdTest(){
        Long id = 1l;
        Book book = createValidBook();
        book.setId(id);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(book));
        //execução
        Optional<Book> foundBook = bookService.getById(id);
        //verificação
        assertThat(foundBook.get().getId()).isEqualTo(id);
        assertThat(foundBook.get().getTitle()).isEqualTo(book.getTitle());
        assertThat(foundBook.get().getAuthor()).isEqualTo(book.getAuthor());
        assertThat(foundBook.get().getIsbn()).isEqualTo(book.getIsbn());

    }

    @Test
    @DisplayName("Deve retornar vazio quando não a um livro por id informado")
    public void bookNotFoundByIdTest(){
        Long id = 1l;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        //execução
        Optional<Book> Book = bookService.getById(id);
        //verificação
        assertThat(Book.isPresent()).isFalse();
    }


    @Test
    @DisplayName("Deve Deletar um livro por id")
    public void deleteBookTest(){
        Long id = 1l;
        Book book = createValidBook();
        book.setId(id);

        //execução
         org.junit.jupiter.api.Assertions.assertDoesNotThrow(()-> bookService.delete(book));

        //verificação
        Mockito.verify( repository, Mockito.times(1)).delete(book);
    }

    @Test
    @DisplayName("Deve ocorrer um erro ao tentar deletar um livro inexistente.")
    public void deleteInvalidBookTest(){

        Book book = new Book();


        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class ,()-> bookService.delete(book));


        //verificação
        Mockito.verify( repository, Mockito.never()).delete(book);
    }

    @Test
    @DisplayName("Deve editar um livro")
    public void updateBookTest() {
        //cenario
        Long id = 1L;

        //livro a atualizar
        Book  book = Book.builder().id(id).build();

        //simulação
        Book updateBook = createValidBook();
        updateBook.setId(id);

        Mockito.when(repository.save(book)).thenReturn(updateBook);

        //execução

        Book updatedBook = bookService.update(book);

        //verificação
        assertThat(updatedBook.getId()).isEqualTo(updateBook.getId());
        assertThat(updatedBook.getAuthor()).isEqualTo(updateBook.getAuthor());
        assertThat(updatedBook.getTitle()).isEqualTo(updateBook.getTitle());
        assertThat(updatedBook.getIsbn()).isEqualTo(updateBook.getIsbn());
    }

    @Test
    @DisplayName("Deve ocorrer um erro ao tentar atualizar um livro inexistente.")
    public void updateInvalidBookTest(){

        Book book = new Book();


        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class ,()-> bookService.update(book));


        //verificação
        Mockito.verify( repository, Mockito.never()).save(book);
    }

    @Test
    @DisplayName("Deve filtrar livros pelas propriedades")
    public void findBookTest(){
        Book book = createValidBook();

        PageRequest pageRequest = PageRequest.of(0,10);

        List<Book> lista = Arrays.asList(book);

        Page<Book> page = new PageImpl<Book>(lista, pageRequest ,1 );
        Mockito.when(repository.findAll(Mockito.any(Example.class), Mockito.any(PageRequest.class)))
                .thenReturn(page);


        Page <Book> result= bookService.find(book, pageRequest);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent()).isEqualTo(lista);
        assertThat(result.getPageable().getPageNumber()).isEqualTo(0);
        assertThat(result.getPageable().getPageSize()).isEqualTo(10);
    }

}
