package com.alex.servletProject;


import com.alex.servletProject.exceptions.StateChangeException;
import com.alex.servletProject.exceptions.SystemException;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Сhanges the state of the {@link Machine}.
 * Date: 12/8/12
 *
 * @author Alex Rakitsky
 */
public class MachineService {

    private static final Logger LOG = Logger.getLogger(MachineService.class);

    private final Map<String, Machine> machineMap;

    /**
     * Used for test.
     * Сhanges the state of the {@link Machine}.
     *
     * @param mockMachineMap map of available machines.
     */
    MachineService(Map<String, Machine> mockMachineMap) {
        this.machineMap = mockMachineMap;
    }

    /**
     * Сhanges the state of the {@link Machine}.
     *
     */
    public MachineService() {
        this.machineMap = new HashMap<String, Machine> ();
    }

    /**
     * Сhanges the state of the {@link Machine}.Looks for the car on the id and changes its state.
     *
     * @param machineId id machine. Look for the available machines,
     *                  if the machine has not been found - called {@link IllegalArgumentException}
     * @param signal    type of signal. Can only be 1 or 0. If incorrect - called {@link IllegalArgumentException}
     * @return status of machine
     * @throws com.alex.servletProject.exceptions.SystemException
     *                              when a system error (Error connecting to the database, read error, etc.)
     * @throws StateChangeException when an error has changed the status,
     *                              error read from various sources (databases, xml, etc.)
     */
    public String setState(String machineId, String signal) throws SystemException, StateChangeException {
        int signalInt;

        //check input values
        if (machineId == null || signal == null) {
            throw new IllegalArgumentException();
        }
        try {
            signalInt = Integer.parseInt(signal);
            if (signalInt != 1 && signalInt != 0) {
                throw new IllegalArgumentException();
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }

        Machine machine = machineMap.get(machineId);
        if (machine == null) {
            machine =new Machine(machineId);
            LOG.info("Create new machine" + machine);
            machineMap.put(machineId,machine);
        }

        return machine.nextState(signalInt);

    }
}
