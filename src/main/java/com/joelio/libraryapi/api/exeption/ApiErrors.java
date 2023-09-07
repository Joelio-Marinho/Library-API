package com.joelio.libraryapi.api.exeption;

import com.joelio.libraryapi.exception.BusinessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    private List<String> errors;

    public ApiErrors(BusinessException errors) {
        this.errors = Arrays.asList(errors.getMessage());
    }

    public ApiErrors(BindingResult bindingResult) {
        this.errors = new ArrayList<>();
        bindingResult.getAllErrors().forEach(error-> this.errors.add(error.getDefaultMessage()) );

    }

    public List<String> getErrors() {
        return errors;
    }

    public ApiErrors(ResponseStatusException errors) {
        this.errors = Arrays.asList(errors.getReason());
    }
}
