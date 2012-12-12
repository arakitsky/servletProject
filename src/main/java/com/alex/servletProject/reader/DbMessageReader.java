package com.alex.servletProject.reader;

import com.alex.servletProject.exceptions.SystemException;

import java.sql.SQLException;

/**
 * Service to read message from database.
 * Date: 12/10/12
 *
 * @author Alex Rakitsky
 */
public class DbMessageReader implements IMessageReader {

    private MachineDAO machineDAO;

    DbMessageReader(MachineDAO machineDAO) {
        this.machineDAO = machineDAO;
    }

    public DbMessageReader() throws SystemException {
        machineDAO = new MachineDAO();
    }

    /**
     * Read the error message from database for the respective machine.
     *
     * @param idMachine unique id name of machine.
     * @return error message
     * @throws SystemException error with the database
     */
    @Override
    public String readMessage(String idMachine) throws SystemException {
        try {
            return machineDAO.findErrorById(idMachine);
        } catch (SQLException | ClassNotFoundException | IllegalArgumentException e) {
            throw new SystemException(e);
        }
    }
}
