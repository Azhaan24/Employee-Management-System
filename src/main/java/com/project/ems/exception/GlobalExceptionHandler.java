package com.project.ems.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("message", "Something went wrong.");
        return "error";
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public String handleEmployeeNotFound(
            EmployeeNotFoundException ex,
            Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }
}