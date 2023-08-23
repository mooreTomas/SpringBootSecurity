package com.example.security.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ModelAndView handleResponseStatusException(ResponseStatusException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("status", ex.getStatusCode());
        modelAndView.addObject("message", ex.getReason());
        return modelAndView;
    }
}