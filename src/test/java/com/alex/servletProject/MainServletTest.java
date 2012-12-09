package com.alex.servletProject;

import com.alex.servletProject.exceptions.MachineException;
import com.alex.servletProject.exceptions.StateChangeException;
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
@Test(groups = "unit")
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
    public void machineServiceResponse() throws ServletException, IOException, MachineException, StateChangeException {
        String signal = "1";
        String machineId = "1";
        request.setParameter(Constants.REQUEST_SIGNAL, signal);
        request.setParameter(Constants.REQUEST_ID_MACHINE, machineId);
        machineService.setState(machineId, signal);
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_OK, response.getStatus());
        verify(machineService);
    }

    @Test
    public void callIllegalArgumentException() throws ServletException, IOException, MachineException, StateChangeException {
        machineService.setState(null, null);
        expectLastCall().andThrow(new IllegalArgumentException());
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_BED, response.getStatus());
        verify(machineService);
    }

    @Test
    public void testMachineExceptionCalled() throws ServletException, IOException, MachineException, StateChangeException {
        machineService.setState(null, null);
        expectLastCall().andThrow(new MachineException());
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_SERVER_ERROR, response.getStatus());
        verify(machineService);
    }

    @Test
    public void testMachineStateChangeExceptionCalled() throws ServletException, IOException, MachineException, StateChangeException {
        machineService.setState(null, null);
        String message = "Test Exception";
        expectLastCall().andThrow(new StateChangeException(message));
        replay(machineService);

        mainServlet.doGet(request, response);

        assertEquals(Constants.RESPONSE_OK, response.getStatus());
        assertEquals(message,response.getContentAsString());
        verify(machineService);
    }
}
