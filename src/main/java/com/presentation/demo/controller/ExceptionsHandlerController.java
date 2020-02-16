package com.presentation.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandlerController extends ResponseEntityExceptionHandler {

    private Logger CONTROLLER_ADVICE_LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public String noHandlerFoundedException(NoHandlerFoundException noHandlerException, HttpServletRequest request, HttpServletResponse response, Model model){
//        CONTROLLER_ADVICE_LOGGER.info("Error processing URL: " + noHandlerException.getRequestURL() + ". " + noHandlerException.getMessage());
//        model.addAttribute("code",response.getStatus());
//        model.addAttribute("message",noHandlerException.getMessage());
//        return "error";
//    }


}
