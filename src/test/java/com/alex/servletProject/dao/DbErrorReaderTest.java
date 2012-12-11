package com.alex.servletProject.dao;

import com.alex.servletProject.exceptions.SystemException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static org.easymock.EasyMock.*;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Test for {@link DbErrorReader}.
 * Date: 12/10/12
 *
 * @author Alex Rakitsky
 */
public class DbErrorReaderTest {

    private MachineDAO machineDAO;
    private DbErrorReader dbErrorReader;

    @BeforeMethod
    public void init() {
        machineDAO = createMock(MachineDAO.class);
        dbErrorReader = new DbErrorReader(machineDAO);
    }

    @Test
    public void testReadError() throws Exception {
        String message = "1";
        String testId = "TestId";
        expect(machineDAO.findErrorById(testId)).andReturn(message);
        replay(machineDAO);

        String result = dbErrorReader.readError(testId);

        assertEquals(message, result);
        verify(machineDAO);
    }

    @Test(expectedExceptions = SystemException.class)
    public void testSQLException() throws Exception {
        String testId = "TestId";
        expect(machineDAO.findErrorById(testId)).andThrow(new SQLException());
        replay(machineDAO);

        dbErrorReader.readError(testId);
    }

    @Test(expectedExceptions = SystemException.class)
    public void testClassNotFoundException() throws Exception {
        String testId = "TestId";
        expect(machineDAO.findErrorById(testId)).andThrow(new ClassNotFoundException());
        replay(machineDAO);

        dbErrorReader.readError(testId);
    }

    @Test(expectedExceptions = SystemException.class)
    public void testIllegalArgumentExceptionException() throws Exception {
        String testId = "TestId";
        expect(machineDAO.findErrorById(testId)).andThrow(new IllegalArgumentException());
        replay(machineDAO);

        dbErrorReader.readError(testId);
    }

}
