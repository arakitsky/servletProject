package com.alex.servletProject.reader;

import com.alex.servletProject.exceptions.SystemException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.SQLException;

import static org.easymock.EasyMock.*;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Test for {@link DbMessageReader}.
 * Date: 12/10/12
 *
 * @author Alex Rakitsky
 */
public class DbErrorReaderTest {

    private MachineDAO machineDAO;
    private DbMessageReader dbErrorReader;

    @BeforeMethod
    public void init() {
        machineDAO = createMock(MachineDAO.class);
        dbErrorReader = new DbMessageReader(machineDAO);
    }

    @Test
    public void testReadError() throws Exception {
        String message = "1";
        String testId = "TestId";
        expect(machineDAO.findErrorById(testId)).andReturn(message);
        replay(machineDAO);

        String result = dbErrorReader.readMessage(testId);

        assertEquals(message, result);
        verify(machineDAO);
    }

    @Test(expectedExceptions = SystemException.class)
    public void testSQLException() throws Exception {
        String testId = "TestId";
        expect(machineDAO.findErrorById(testId)).andThrow(new SQLException());
        replay(machineDAO);

        dbErrorReader.readMessage(testId);
    }

    @Test(expectedExceptions = SystemException.class)
    public void testClassNotFoundException() throws Exception {
        String testId = "TestId";
        expect(machineDAO.findErrorById(testId)).andThrow(new ClassNotFoundException());
        replay(machineDAO);

        dbErrorReader.readMessage(testId);
    }

    @Test(expectedExceptions = SystemException.class)
    public void testIllegalArgumentExceptionException() throws Exception {
        String testId = "TestId";
        expect(machineDAO.findErrorById(testId)).andThrow(new IllegalArgumentException());
        replay(machineDAO);

        dbErrorReader.readMessage(testId);
    }

}
