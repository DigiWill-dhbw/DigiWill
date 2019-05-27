package de.digiwill.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ErrorHandlingController {

    // We fucked up
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(Exception.class)
    public String internalServerError() {
        return "error";
    }

    // user fucked up
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(Exception.class)
    public String badRequest() {
        return "error";
    }

}
