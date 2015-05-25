package org.tcd.activityforecast.controller;

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
public class RestControllerAdvice {

    
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo numberFormatException(Exception exception, WebRequest request) {
        return new ErrorInfo(message: "Invalid Number " + exception.localizedMessage, url: request.getDescription(false))
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo illegalArgument(Exception exception, WebRequest request) {
        return new ErrorInfo(message: exception.localizedMessage, url: request.getDescription(false))
    }
    
    
    @ExceptionHandler(java.time.DateTimeException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo invalidDateTime(Exception exception, WebRequest request) {
        return new ErrorInfo(message: exception.localizedMessage, url: request.getDescription(false))
    }
}
