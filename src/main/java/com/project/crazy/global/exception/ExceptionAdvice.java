package com.project.crazy.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> onUnhandledException(Exception ex) {
        return new ResponseEntity<>(new ExceptionDto("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionDto> onBusinessException(BusinessException ex) {
        return new ResponseEntity<>(new ExceptionDto(ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ExceptionDto> onBindException(BindException ex) {
        FieldError error = ex.getFieldError();
        String errorMessage = String.format("%s: %s", error.getField(), error.getDefaultMessage());
        return new ResponseEntity<>(new ExceptionDto(errorMessage), HttpStatus.BAD_REQUEST);
    }



}


