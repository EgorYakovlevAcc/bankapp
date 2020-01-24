package com.presentation.demo.errors.persistance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//@ControllerAdvice
//public class ConstraintViolationExceptionsHandler extends ResponseEntityExceptionHandler {
//    @ExceptionHandler(ThereIsNoSuchUserException.class)
//    protected ResponseEntity<AwesomeException> handleThereIsNoSuchUserException() {
//        return new ResponseEntity<>(new AwesomeException("There is no such user"), HttpStatus.NOT_FOUND);
//    }
//
//}