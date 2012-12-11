package com.alex.servletProject;

import com.alex.servletProject.exceptions.StateChangeException;
import com.alex.servletProject.exceptions.SystemException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Test for {@link MainServlet}.
 * Date: 12/8/12
 *
 * @author Alex Rakitsky
 */
public class MainServletTest {

    private MachineService machineService;
    private MainServlet mainServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeMethod
    public void init() {
        machineService = createMock(MachineService.class);
        mainServlet = new MainServlet(machineService);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void machineServiceResponse() throws ServletException, IOException, SystemException, StateChangeException {
        String signal = "1";
        String machineId = "1";
        String message = "Test message";
        request.setParameter(Constants.REQUEST_SIGNAL, signal);
        request.setParameter(Constants.REQUEST_ID_MACHINE, machineId);
        expect(machineService.setState(machineId, signal)).andReturn(message);
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_OK, response.getStatus());
        assertEquals(message, response.getContentAsString());
        verify(machineService);
    }

    @Test
    public void callIllegalArgumentException() throws ServletException, IOException, SystemException, StateChangeException {
        machineService.setState(null, null);
        expectLastCall().andThrow(new IllegalArgumentException());
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_BED, response.getStatus());
        verify(machineService);
    }

    @Test
    public void testSystemExceptionCalled() throws ServletException, IOException, SystemException, StateChangeException {
        machineService.setState(null, null);
        expectLastCall().andThrow(new SystemException());
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_SERVER_ERROR, response.getStatus());
        verify(machineService);
    }

    @Test
    public void testMachineStateChangeExceptionCalled() throws ServletException, IOException, SystemException, StateChangeException {
        machineService.setState(null, null);
        String message = "Test Exception";
        expectLastCall().andThrow(new StateChangeException(message));
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_OK, response.getStatus());
        assertEquals(message, response.getContentAsString());
        verify(machineService);
    }
}
