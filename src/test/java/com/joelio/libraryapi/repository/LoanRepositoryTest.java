package com.joelio.libraryapi.repository;

import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.model.Loan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LoanRepository loanRepository;

    @Test
    @DisplayName("Deve verificar se existe emprestimo não devolvido para o livro")
    public void existsByBookNotReturnedTest(){

        //cenario
        Book book = Book.builder().author("joelio").title("as aventuras").isbn("1234").build();
        entityManager.persist(book);

        Loan loan = Loan.builder().book(book).localDate(LocalDate.now()).customer("joelio").build();
        entityManager.persist(loan);

        //execução
        boolean exists =  loanRepository.existsByBookAndNotReturned(book.getId());

        assertThat(exists).isTrue();
    }

}
