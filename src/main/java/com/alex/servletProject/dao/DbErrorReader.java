package com.alex.servletProject.dao;

import com.alex.servletProject.IErrorReader;
import com.alex.servletProject.exceptions.SystemException;

import java.sql.SQLException;

/**
 * Service to read message from database.
 * Date: 12/10/12
 *
 * @author Alex Rakitsky
 */
public class DbErrorReader implements IErrorReader {

    private MachineDAO machineDAO;

    DbErrorReader(MachineDAO machineDAO) {
        this.machineDAO = machineDAO;
    }

    public DbErrorReader() throws SystemException {
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
    public String readError(String idMachine) throws SystemException {
        try {
            return machineDAO.findErrorById(idMachine);
        } catch (SQLException | ClassNotFoundException e) {
            throw new SystemException(e);
        }
    }
}
