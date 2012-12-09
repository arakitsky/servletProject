package com.alex.servletProject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Test for MainServlet class.
 * Date: 12/8/12
 *
 * @author Alex Rakitsky
 */
public class MainServletTest {

    private MachineService machineService;
    private MainServlet mainServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @Before
    public void init() {
        machineService = createMock(MachineService.class);
        mainServlet = new MainServlet(machineService);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void machineServiceResponse() throws ServletException, IOException, MachineException {
        String signal = "1";
        String machineId = "1";
        String responseMessage = "Test_Message";
        request.setParameter(Constants.REQUEST_SIGNAL, signal);
        request.setParameter(Constants.REQUEST_ID_MACHINE, machineId);
        expect(machineService.setState(machineId, signal)).andReturn(responseMessage);
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_OK, response.getStatus());
        assertEquals(responseMessage, response.getContentAsString());
        verify(machineService);
    }

    @Test
    public void callIllegalArgumentException() throws ServletException, IOException, MachineException {
        expect(machineService.setState(null, null)).andThrow(new IllegalArgumentException());
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_BED, response.getStatus());
        verify(machineService);
    }

    @Test
    public void testMachineExceptionCalled() throws ServletException, IOException, MachineException {
        expect(machineService.setState(null, null)).andThrow(new MachineException());
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_SERVER_ERROR, response.getStatus());
        verify(machineService);
    }
}
