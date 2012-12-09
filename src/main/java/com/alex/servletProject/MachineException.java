package com.alex.servletProject;

/**
 * Called when an error of class {@link MachineService} (DB connection error, not found id machine)
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
