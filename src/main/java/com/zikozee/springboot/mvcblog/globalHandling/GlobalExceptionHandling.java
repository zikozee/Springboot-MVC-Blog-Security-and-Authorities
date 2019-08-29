package com.zikozee.springboot.mvcblog.globalHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {
        // already handled
//    @ExceptionHandler
//    public ResponseEntity<UserErrorResponse> handleException(UserNotFoundException ex) {
//        // create a userErrorResponse
//        UserErrorResponse error = new UserErrorResponse();
//
//        error.setStatus(HttpStatus.NOT_FOUND.value());
//        error.setMessage(ex.getMessage());
//        error.setTimeStamp(System.currentTimeMillis());
//        //Return ResponseEntity
//        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(Exception exc) {
        // create a userErrorResponse
        UserErrorResponse error = new UserErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage("Not a Valid Id");
        error.setTimeStamp(System.currentTimeMillis());
        //Return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
