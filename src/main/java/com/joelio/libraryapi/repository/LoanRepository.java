package com.joelio.libraryapi.repository;

import com.joelio.libraryapi.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    @Query(value = "select case when (count (l.id) > 0 ) then true else false end " +
           " from Loan l where l.book_id = ?1 and ( l.returnd is null or l.returnd is false )  ",nativeQuery = true)
    boolean existsByBookAndNotReturned( Long bookId);
}
