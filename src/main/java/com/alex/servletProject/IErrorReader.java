package com.alex.servletProject;

import com.alex.servletProject.exceptions.SystemException;

/**
 * Service to read message from a remote location.
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
public interface IErrorReader {

    /**
     * Read the error message for the respective machine.
     * @param idMachine unique id name of machine.
     * @return error message
     * @throws SystemException an error of application (error сonnect to your database, the properties file is not found, etc.)
     */
    public String readError(String idMachine) throws SystemException;
}
