package com.ukim.finki.learn2cookbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(
            value = HttpStatus.NOT_FOUND,
            reason = "requested receipt not found")
    @ExceptionHandler(ReceiptNotFoundException.class)
    public void handleException(ReceiptNotFoundException e) {
    }

    @ResponseStatus(
            value = HttpStatus.NOT_FOUND,
            reason = "requested user not found")
    @ExceptionHandler(UserNotFoundException.class)
    public void handleException(UserNotFoundException e) {
    }

    @ResponseStatus(
            value = HttpStatus.UNAUTHORIZED,
            reason = "specified credentials not found")
    @ExceptionHandler(BadCredentialsException.class)
    public void handleException(BadCredentialsException e) {
    }
}
