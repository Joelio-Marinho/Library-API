package com.joelio.libraryapi.service;

import com.joelio.libraryapi.DTO.ReturnedLoanDTO;
import com.joelio.libraryapi.model.Loan;

import java.util.Optional;

public interface LoanService {
    Loan save(Loan loan);

    Optional<Loan> getById(Long id);

    Loan update(Long id, ReturnedLoanDTO loan);
}
