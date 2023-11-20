package com.breiner.tesis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFound(ResourceNotFound e) {
        return "Not found";
    }

    @ExceptionHandler(AdoptionPetAlreadyAdopted.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAdoptionPetAdopted(AdoptionPetAlreadyAdopted e) {
        return "Already adopted";
    }

    @ExceptionHandler(UserAlreadyExists.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAdoptionPetAdopted(UserAlreadyExists e) {
        return "User already exits";
    }
}
