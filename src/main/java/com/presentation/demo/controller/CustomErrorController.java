package com.presentation.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
public class CustomErrorController implements ErrorController {


    private Logger ERROR_HANDLER_CONTROLLER = LoggerFactory.getLogger(CustomErrorController.class);


    @GetMapping("/error")
    private String errorHandler(HttpServletRequest request, Model model){
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer)  request.getAttribute("javax.servlet.error.status_code");

        ERROR_HANDLER_CONTROLLER.info("Error processing URL: " + "\"" + request.getAttribute("javax.servlet.error.request_uri)") + "\" +" +
                ". Exception type: " + (exception != null ? exception.getMessage() : "N/A"));

        model.addAttribute("code", statusCode);
        model.addAttribute("message", "Error");
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
