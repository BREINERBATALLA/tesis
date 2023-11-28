package com.breiner.tesis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(errores);
    }
}
