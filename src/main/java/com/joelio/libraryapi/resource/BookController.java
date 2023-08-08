package com.joelio.libraryapi.resource;

import com.joelio.libraryapi.DTO.BookDTO;
import com.joelio.libraryapi.api.exeption.ApiErrors;
import com.joelio.libraryapi.exception.BusinessException;
import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService service;
    private ModelMapper modelMapper;

    public BookController(BookService service, ModelMapper modelMapper){
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody @Valid BookDTO dto){
      Book entity = modelMapper.map(dto, Book.class);

      entity = service.save(entity);

        return modelMapper.map(entity,BookDTO.class);
    }

    @GetMapping("{id}")
    public BookDTO get(@PathVariable Long id){

        Book book = service.getById(id).get();

        return modelMapper.map(book,BookDTO.class);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException exception){
        BindingResult bindingResult =  exception.getBindingResult();

        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleBuisnessException(BusinessException exception){

        return new ApiErrors(exception);
    }
}
