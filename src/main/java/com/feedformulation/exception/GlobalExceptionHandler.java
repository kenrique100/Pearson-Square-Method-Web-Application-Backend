package com.feedformulation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles InvalidInputException and returns a 400 Bad Request response.
     * This method is triggered whenever an InvalidInputException is thrown.
     *
     * @param ex The InvalidInputException instance.
     * @return A ResponseEntity with the exception message and 400 Bad Request status.
     */
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<String> handleInvalidInputException(InvalidInputException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles validation exceptions thrown during method argument validation.
     * Captures the validation errors and returns them in a map with field names as keys
     * and error messages as values.
     *
     * @param ex The MethodArgumentNotValidException instance.
     * @return A ResponseEntity with the validation errors map and 400 Bad Request status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        // Iterates over all field errors and collects them into the errors map
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * Handles any general exceptions that are not explicitly handled by other methods.
     * Returns a generic error message with a 500 Internal Server Error status.
     *
     * @param ex The Exception instance.
     * @return A ResponseEntity with a generic error message and 500 Internal Server Error status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}
