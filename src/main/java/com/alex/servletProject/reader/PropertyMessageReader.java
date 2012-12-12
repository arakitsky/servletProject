package com.alex.servletProject.reader;

import com.alex.servletProject.Constants;
import com.alex.servletProject.exceptions.SystemException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Service to read message from a properties file.
 * Date: 12/12/12
 *
 * @author Alex Rakitsky
 */
public class PropertyMessageReader implements IMessageReader {

    String path;

    /**
     * @param path path to property file.
     */
    public PropertyMessageReader(String path) {
        this.path = path;
    }

    /**
     * Read the error message for the respective machine. If property not found throw {@link SystemException}.
     *
     * @param idMachine unique id name of machine.
     * @return error message
     * @throws SystemException property not found, read property file exception.
     */
    @Override
    public String readMessage(String idMachine) throws SystemException {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(path));
            String message = prop.getProperty(Constants.PROP_MACHINE_ID + '.' + idMachine);
            if (message == null) {
                throw new SystemException("Message for machine id = " + idMachine + " is not found");
            }
            return message;
        } catch (IOException e) {
            throw new SystemException(e);
        }
    }
}
