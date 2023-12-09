package com.example.ProjectV1.handler;

import com.example.ProjectV1.Exception.ObjectCollectionException;
import com.example.ProjectV1.Exception.ObjectNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleException(IllegalStateException exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }
    @ExceptionHandler(ObjectCollectionException.class)
    public ResponseEntity<?> handleException(ObjectCollectionException exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }
    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleExcpetion(ObjectNotValidException exception)
    {
        return ResponseEntity
                .badRequest()
                .body(exception.getErrorMessages());
    }
}
