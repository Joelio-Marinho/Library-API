package com.joelio.libraryapi.resource;


import com.joelio.libraryapi.DTO.BookDTO;
import com.joelio.libraryapi.api.exeption.ApiErrors;
import com.joelio.libraryapi.exception.BusinessException;
import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.validation.BindingResultUtils.getBindingResult;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService service;

    public BookController(BookService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto){
      Book entity = service.save(Book.builder()
                                  .id(dto.getId())
                                  .title(dto.getTitle())
                                  .author(dto.getAuthor())
                                  .isbn(dto.getIsbn()).build());

        return BookDTO.builder()
                        .id(entity.getId())
                        .title(entity.getTitle())
                        .author(entity.getAuthor())
                        .isbn(entity.getIsbn()).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException exception){
        BindingResult bindingResult =  exception.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBuisnessException(BusinessException exception){

        return new ApiErrors(exception);
    }
}
