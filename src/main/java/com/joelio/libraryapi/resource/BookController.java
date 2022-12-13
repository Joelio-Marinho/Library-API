package com.joelio.libraryapi.resource;


import com.joelio.libraryapi.DTO.BookDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/books")
public class BookController {

    @PostMapping
    public BookDTO create(){

    }
}
