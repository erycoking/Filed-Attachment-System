package com.egerton.bugs.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateInputException;

@Controller("error")
public class MyErrorController  implements ErrorController{

    @Override
    public String getErrorPath() {
        return null;
    }

    @GetMapping("/error")
    public ModelAndView getErrorPage(){
        return new ModelAndView("errors", "errorMessage", "An error occurred while processing your request.");
    }

    /*Access denied page*/
    @GetMapping("/access-denied")
    public ModelAndView getAccessDeniedPage(){
        return new ModelAndView("errors", "errorMessage", "You do not have sufficient privilege to process this request request.");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle404Exception(NoHandlerFoundException ex) {
        //do whatever you want
        return new ModelAndView("errors", "errorMessage", "The page you requested for does not exist.");
    }

    @ExceptionHandler(TemplateInputException.class)
    public ModelAndView handleException(TemplateInputException ex) {
        //do whatever you want
        return new ModelAndView("errors", "errorMessage", "The page you requested for does not exist.");
    }
}
