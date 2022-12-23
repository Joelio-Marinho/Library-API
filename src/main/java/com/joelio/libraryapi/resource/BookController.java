package com.joelio.libraryapi.resource;


import com.joelio.libraryapi.DTO.BookDTO;
import com.joelio.libraryapi.model.Book;
import com.joelio.libraryapi.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService service;

    public BookController(BookService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@RequestBody  BookDTO dto){
      Book entity = service.save(new Book(dto.getId(),dto.getTitle(),dto.getAuthor(), dto.getIsbn()));

        return new BookDTO(entity.getId(),entity.getTitle(),entity.getAuthor(), entity.getIsbn());
    }
}
