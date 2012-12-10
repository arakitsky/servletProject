package com.alex.servletProject.dao;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static org.testng.Assert.assertEquals;

/**
 * Test class for {@link MachineDAO}.
 * Date: 12/10/12
 *
 * @author Alex Rakitsky
 */
public class MachineDAOTest {

    public static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
    public static final String URL = "jdbc:hsqldb:";
    public static final String DB_NAME = "testDb";
    public static final String USER = "su";
    public static final String PASSWORD = "";
    private MachineDAO machineDAO;

    @BeforeMethod
    public void init() throws SQLException, ClassNotFoundException {
        machineDAO = new MachineDAO(JDBC_DRIVER, URL, DB_NAME, USER, PASSWORD);
        machineDAO.createTable();
    }

    @AfterMethod
    public void destroyDb() throws Exception {
        machineDAO.dropTable();
        machineDAO.close();
    }

    @Test
    public void testAddAndFind() throws Exception {
        String id = "2";
        String message = "Test machine error 2";
        machineDAO.addMachineError("1", "Test machine error 1");
        machineDAO.addMachineError("3", "Test machine error 3");
        machineDAO.addMachineError(id, message);

        String result = machineDAO.findErrorById(id);

        assertEquals(message, result);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testAddAndFindMachineNotFound() throws Exception {
        String id = "2";
        String message = "Test machine error 2";
        machineDAO.addMachineError("1", "Test machine error 1");
        machineDAO.addMachineError("3", "Test machine error 3");
        machineDAO.addMachineError(id, message);

        String result = machineDAO.findErrorById("5");

        assertEquals(message, result);
    }

    @Test
    public void testCreateExistTable() throws SQLException, ClassNotFoundException {
        //When you create an existing table, nothing is going to happen
        machineDAO.createTable();
        machineDAO.createTable();
    }

}
