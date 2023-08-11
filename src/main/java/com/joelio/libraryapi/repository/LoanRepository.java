package com.joelio.libraryapi.repository;

import com.joelio.libraryapi.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Long, Loan> {
}
