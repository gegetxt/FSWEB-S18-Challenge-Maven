package com.workintech.fswebs18challengemaven.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CardErrorResponse> handleCardException(CardException exception) {
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(new CardErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<CardErrorResponse> handleGenericException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CardErrorResponse(exception.getMessage()));
    }
}
