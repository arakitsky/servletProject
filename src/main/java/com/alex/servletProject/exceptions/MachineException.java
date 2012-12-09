package com.alex.servletProject.exceptions;

/**
 * Сalled when a system error occurs
 * (DB connection error, not found id machine)
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
public class MachineException extends Exception {

    /**
     * Constructs an <code>MachineException</code> with no
     * detail message.
     */
    public MachineException() {
        super();
    }

}
