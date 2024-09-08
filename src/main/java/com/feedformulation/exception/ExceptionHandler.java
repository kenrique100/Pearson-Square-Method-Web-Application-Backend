package com.feedformulation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    /**
     * Handles all exceptions that are not explicitly caught by other exception handlers.
     *
     * @param e The exception that was thrown
     * @return A ResponseEntity containing the error message and an HTTP 500 Internal Server Error status
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class) // Specifies that this method will handle all exceptions of type Exception
    public ResponseEntity<String> handleException(Exception e) {
        // Returns the exception message with HTTP status 500 (Internal Server Error)
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
