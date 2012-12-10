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

}
