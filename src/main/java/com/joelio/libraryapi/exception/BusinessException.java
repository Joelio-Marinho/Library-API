package com.joelio.libraryapi.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String isbnJaCadastrado) {
        super(isbnJaCadastrado);
    }
}
