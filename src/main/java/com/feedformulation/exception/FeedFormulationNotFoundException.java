package com.feedformulation.exception;

public class FeedFormulationNotFoundException extends RuntimeException {

    /**
     * Constructor that accepts a custom error message.
     *
     * @param message The error message to be displayed when the exception is thrown
     */
    public FeedFormulationNotFoundException(String message) {
        super(message); // Calls the parent RuntimeException constructor with the custom message
    }

    /**
     * Constructor that accepts a custom error message and the cause of the exception.
     *
     * @param message The error message to be displayed when the exception is thrown
     * @param cause The root cause of the exception (another throwable that triggered this exception)
     */
    public FeedFormulationNotFoundException(String message, Throwable cause) {
        super(message, cause); // Calls the parent RuntimeException constructor with the message and cause
    }
}
