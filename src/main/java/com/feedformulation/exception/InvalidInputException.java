package com.feedformulation.exception;

/**
 * Custom exception class to handle invalid input scenarios.
 * This class extends RuntimeException, allowing it to be thrown
 * during the normal operation of the program without being declared.
 */
public class InvalidInputException extends RuntimeException {

    /**
     * Constructor that takes an error message.
     * The message describes the reason for the exception.
     *
     * @param message A string containing the error message.
     */
    public InvalidInputException(String message) {
        super(message);  // Passes the error message to the parent RuntimeException class.
    }
}
