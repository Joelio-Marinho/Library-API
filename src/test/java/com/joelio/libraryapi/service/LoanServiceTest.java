package com.joelio.libraryapi.service;

import com.joelio.libraryapi.exception.BusinessException;
import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.model.Loan;
import com.joelio.libraryapi.repository.LoanRepository;
import com.joelio.libraryapi.service.impl.BookServiceImpl;
import com.joelio.libraryapi.service.impl.LoanServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LoanServiceTest {

    LoanService service;

    @MockBean
    LoanRepository repository;

    @BeforeEach
    public void setUp() {
        this.service = new LoanServiceImp(repository);
    }
    @Test
    @DisplayName("deve salvar um emprestimo")
    public void saveLoanTest(){

        Book book = Book.builder().id(1l).author("joelio").isbn("123").build();
        Loan savingLoan = Loan.builder().book(book).customer("fulano").localDate(LocalDate.now()).build();

        Loan savedLoan = Loan.builder().id(1l).book(book).customer("fulano").localDate(LocalDate.now()).build();

        Mockito.when(repository.existsByBookAndNotReturned(book.getId())).thenReturn(false);
        Mockito.when(repository.save(savingLoan)).thenReturn(savedLoan);

        Loan loan = service.save(savingLoan);

        assertThat(loan.getId()).isEqualTo(savedLoan.getId());
        assertThat(loan.getBook().getId()).isEqualTo(savedLoan.getBook().getId());
        assertThat(loan.getCustomer()).isEqualTo(savedLoan.getCustomer());
        assertThat(loan.getLocalDate()).isEqualTo(savedLoan.getLocalDate());

    }

    @Test
    @DisplayName("Deve lancar erro de negocio ao salvar um emprestimo com livro ja emprestado")
    public void LoanedBookSaveTest(){

        Book book = Book.builder().id(1l).author("joelio").isbn("123").build();
        Loan savingLoan = Loan.builder().book(book).customer("fulano").localDate(LocalDate.now()).build();

        Mockito.when(repository.existsByBookAndNotReturned(book.getId())).thenReturn(true);


        Throwable exception = catchThrowable(() -> service.save(savingLoan));

        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Book already loaned");

        verify(repository,never()).save(savingLoan);
    }


}
