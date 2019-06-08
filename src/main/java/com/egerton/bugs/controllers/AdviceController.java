package com.egerton.bugs.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AdviceController {

    @ExceptionHandler(value = Exception.class)
    private ModelAndView returnErrorPage(Exception ex){
       return new ModelAndView("errors", "errorMessage", "An error occurred while processing your request.");
    }


}
