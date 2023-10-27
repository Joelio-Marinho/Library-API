package com.joelio.libraryapi.service.impl;

import com.joelio.libraryapi.DTO.ReturnedLoanDTO;
import com.joelio.libraryapi.exception.BusinessException;
import com.joelio.libraryapi.model.Loan;
import com.joelio.libraryapi.repository.LoanRepository;
import com.joelio.libraryapi.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanServiceImp implements LoanService {


    private LoanRepository repository;

    @Autowired
    public LoanServiceImp(LoanRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Loan> getById(Long id){
        return this.repository.findById(id);
    }

    @Override
    public Loan update(Long id, ReturnedLoanDTO dto) {

        if(repository.existsById(id)){
            Loan loanUpdate = getById(id).get();
            loanUpdate.setReturned(dto.getReturned());
            return repository.save(loanUpdate);
        }
        throw  new BusinessException("Book does not exist");
    }

    @Override
    public Loan save(Loan loan) {
        if(repository.existsByBookAndNotReturned(loan.getBook().getId())){
            throw  new BusinessException("Book already loaned");
        }
        return repository.save(loan);
    }


}
