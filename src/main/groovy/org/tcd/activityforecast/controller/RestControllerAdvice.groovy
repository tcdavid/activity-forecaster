package org.tcd.activityforecast.controller;

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest

//@ControllerAdvice(annotations = RestController.class)
@ControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo invalidArgument(Exception exception, WebRequest request) {
        
        return new ErrorInfo(message: exception.localizedMessage, url: request.getDescription(false))
    }
}
