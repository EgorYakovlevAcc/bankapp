package com.presentation.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandlerController extends ResponseEntityExceptionHandler {

    private Logger CONTROLLER_ADVICE_LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

}
