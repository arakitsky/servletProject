package com.alex.servletProject.exceptions;

/**
 * Called when an error, change the status of {@link com.alex.servletProject.Machine#nextState()}.
 * Message will be displayed on the html page.
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
public class StateChangeException extends Exception {
    /**
     * Called when an error, change the status of {@link com.alex.servletProject.Machine#nextState()}.
     * @param message error message. Will be displayed on the html page.
     */
    public StateChangeException(String message) {
        super(message);
    }
}
