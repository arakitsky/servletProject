package com.alex.servletProject;

import com.alex.servletProject.exceptions.MachineException;
import com.alex.servletProject.exceptions.StateChangeException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * .
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
public class Machine {

    private final String id;

    public Machine(String id) {
        this.id=id;
    }

    public void nextState() throws MachineException, StateChangeException {
        throw new NotImplementedException();
    }
}
