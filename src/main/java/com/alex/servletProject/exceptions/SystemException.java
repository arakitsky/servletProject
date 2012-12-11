package com.alex.servletProject.exceptions;

/**
 * Сalled when a system error occurs
 * (DB connection error, not found id machine)
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
public class SystemException extends Exception {

    /**
     * Constructs an <code>SystemException</code> with no
     * detail message.
     */
    public SystemException() {
        super();
    }

    /**
     * Sends an error message up the hierarchy.
     */
    public SystemException(Throwable cause) {
        super(cause);
    }

    /**
     * Send message with description
     */
    public SystemException(String message) {
        super(message);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
