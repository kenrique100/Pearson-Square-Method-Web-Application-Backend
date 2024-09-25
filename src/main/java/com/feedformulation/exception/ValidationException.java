package com.feedformulation.exception;

/**
 * Custom exception class for handling validation errors.
 * This class extends RuntimeException, allowing it to be used
 * when validation fails during the program execution.
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructor that takes a validation error message.
     * The message describes the validation failure.
     *
     * @param message A string containing the validation error message.
     */
    public ValidationException(String message) {
        super(message);  // Passes the validation error message to the parent RuntimeException class.
    }
}
