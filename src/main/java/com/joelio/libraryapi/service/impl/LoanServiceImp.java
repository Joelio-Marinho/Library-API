package com.joelio.libraryapi.service.impl;

import com.joelio.libraryapi.model.Loan;
import com.joelio.libraryapi.repository.LoanRepository;
import com.joelio.libraryapi.service.LoanService;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImp implements LoanService {

    private LoanRepository repository;
    @Override
    public Loan save(Loan loan) {
        return loan;
    }
}
