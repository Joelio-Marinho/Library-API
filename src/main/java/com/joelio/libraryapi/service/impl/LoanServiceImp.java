package com.joelio.libraryapi.service.impl;

import com.joelio.libraryapi.exception.BusinessException;
import com.joelio.libraryapi.model.Loan;
import com.joelio.libraryapi.repository.LoanRepository;
import com.joelio.libraryapi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImp implements LoanService {

    private LoanRepository repository;

    public LoanServiceImp(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Loan save(Loan loan) {
        if(repository.existsByBookAndNotReturned(loan.getBook().getId())){
            throw  new BusinessException("Book already loaned");
        }
        return repository.save(loan);
    }
}
