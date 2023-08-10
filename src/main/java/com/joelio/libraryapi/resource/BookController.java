package com.joelio.libraryapi.resource;

import com.joelio.libraryapi.DTO.BookDTO;
import com.joelio.libraryapi.api.exeption.ApiErrors;
import com.joelio.libraryapi.exception.BusinessException;
import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

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
        return  service.getById(id).map(book -> modelMapper.map(book,BookDTO.class))
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        Book book = service.getById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(book);
    }

    @PutMapping("{id}")
    public BookDTO update(@PathVariable Long id, BookDTO dto){
       return service.getById(id).map(book ->{
        book.setAuthor(dto.getAuthor());
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        Book bookUpdate = service.update(book);
        return modelMapper.map(bookUpdate,BookDTO.class);})
           .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    @GetMapping
    public Page<BookDTO> find(BookDTO bookDTO, Pageable pageableRequest){
       Book filter=  modelMapper.map(bookDTO, Book.class);
       Page<Book> result = service.find(filter,pageableRequest);

       List<BookDTO> list = result.getContent().stream()
               .map(entity -> modelMapper.map(entity,BookDTO.class))
               .collect(Collectors.toList());

       return new PageImpl<BookDTO>(list, pageableRequest, result.getTotalElements());
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
