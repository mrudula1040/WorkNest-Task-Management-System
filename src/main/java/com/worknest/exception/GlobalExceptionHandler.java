package com.worknest.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExists(
            UserAlreadyExistsException ex,
            Model model
    ) {
        model.addAttribute("error", ex.getMessage());
        return "signup"; // same signup page
    }
}
