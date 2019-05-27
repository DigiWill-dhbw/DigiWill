package de.digiwill.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ErrorHandlingController {

    // We fucked up
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    @ExceptionHandler(Exception.class)
    public String internalServerError() {
        return "errorpage";
    }

    // user fucked up
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler(Exception.class)
    public String badRequest() {
        return "errorpage";
    }

    // Convert a predefined exception to an HTTP Status code
    @ResponseStatus(value= HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(Exception.class)
    public String pageNotFound() {
        return "errorpage";
    }


}
