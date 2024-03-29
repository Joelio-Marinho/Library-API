package com.joelio.libraryapi.resource;


import com.joelio.libraryapi.DTO.LoanDTO;
import com.joelio.libraryapi.DTO.ReturnedLoanDTO;
import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.model.Loan;
import com.joelio.libraryapi.service.BookService;
import com.joelio.libraryapi.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/Loans")
@RequiredArgsConstructor
public class LoanController {
    private  LoanService loanService;
    private  BookService bookService;
    private ModelMapper modelMapper;

    public LoanController(LoanService loanService, BookService bookService, ModelMapper modelMapper) {
        this.loanService = loanService;
        this.bookService = bookService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody LoanDTO dto){
        Book book = bookService.getBookByIsbn(dto.getIsbn()).orElseThrow(()->
                 new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book not found for passed isbn"));
        Loan entity = Loan.builder()
                .book(book)
                .localDate(LocalDate.now())
                .build();
        entity = loanService.save(entity);
        return entity.getId();
    }

    @PatchMapping("{id}")
    public void returnBook(@PathVariable Long id, @RequestBody ReturnedLoanDTO dto){
      loanService.update(id,dto);
    }

}
