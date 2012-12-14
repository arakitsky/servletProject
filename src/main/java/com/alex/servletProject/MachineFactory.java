package com.alex.servletProject;

import com.alex.servletProject.exceptions.SystemException;

/**
 * .
 * Date: 12/14/12
 *
 * @author Alex Rakitsky
 */
public class MachineFactory {

    public Machine createMachine(String id) throws SystemException {
        return new Machine(id);
    }
}
