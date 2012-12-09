package com.alex.servletProject;

/**
 *
 * Date: 12/8/12
 *
 * @author Alex Rakitsky
 */
public class MachineService {
    public String setState(String machineId, String signal) throws MachineException {
        return "MachineId = " + machineId + "; signal = " + signal;
    }
}
