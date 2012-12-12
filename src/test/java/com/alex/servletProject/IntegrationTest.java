package com.alex.servletProject;

import com.alex.servletProject.exceptions.SystemException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Integration and acceptance test for system.
 * Date: 12/12/12
 *
 * @author Alex Rakitsky
 */
public class IntegrationTest {


    public static final String ID_MACHINE_CORRECT = "1";
    private MainServlet mainServlet;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeMethod
    public void init() throws ClassNotFoundException, SQLException, SystemException {
        mainServlet = new MainServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void allStateOk() throws SystemException, ServletException, IOException {

        //change machine current state from NONE to STATE_1.
        sendChangeSignal(String.valueOf(State.NONE.getSignalChange()));
        //change machine current state from STATE_1 to STATE_2.
        sendChangeSignal(String.valueOf(State.STATE_1.getSignalChange()));
        //change machine current state from STATE_2 to STATE_2.
        sendChangeSignal(String.valueOf(State.STATE_2.getSignalChange()));

        assertEquals(Constants.MESSAGE_STATES_COMPLETED, response.getContentAsString());
    }

    @Test
    public void incorrectState2() throws SystemException, ServletException, IOException {

        //change machine current state from NONE to STATE_1.
        sendChangeSignal(String.valueOf(State.NONE.getSignalChange()));
        //change machine current state from STATE_1 to STATE_2.
        sendChangeSignal(String.valueOf(State.STATE_1.getSignalChange()));
        //change machine current state from STATE_2 to STATE_2.
        sendChangeSignal(String.valueOf(incorrectState(State.STATE_2)));

        assertFalse(Constants.MESSAGE_STATES_COMPLETED.equalsIgnoreCase(response.getContentAsString()));
    }

    @Test
    public void incorrectState1() throws SystemException, ServletException, IOException {

        //change machine current state from NONE to STATE_1.
        sendChangeSignal(String.valueOf(State.NONE.getSignalChange()));
        //change machine current state from STATE_1 to STATE_2.
        sendChangeSignal(String.valueOf(incorrectState(State.STATE_1)));
        //change machine current state from STATE_2 to STATE_2.
        sendChangeSignal(String.valueOf(State.STATE_2.getSignalChange()));

        assertFalse(Constants.MESSAGE_STATES_COMPLETED.equalsIgnoreCase(response.getContentAsString()));
    }

    @Test
    public void incorrectStateNone() throws SystemException, ServletException, IOException {

        //change machine current state from NONE to STATE_1.
        sendChangeSignal(String.valueOf(incorrectState(State.NONE)));
        //change machine current state from STATE_1 to STATE_2.
        sendChangeSignal(String.valueOf(State.STATE_1.getSignalChange()));
        //change machine current state from STATE_2 to STATE_2.
        sendChangeSignal(String.valueOf(State.STATE_2.getSignalChange()));

        assertFalse(Constants.MESSAGE_STATES_COMPLETED.equalsIgnoreCase(response.getContentAsString()));
    }

    @Test
    public void changeState2Machine() throws SystemException, ServletException, IOException {
        String correctId2 = "2";

        //change machine current state from NONE to STATE_1.
        sendChangeSignal(String.valueOf(State.NONE.getSignalChange()));
        sendChangeSignal(correctId2, String.valueOf(State.NONE.getSignalChange()));
        //change machine current state from STATE_1 to STATE_2.
        sendChangeSignal(String.valueOf(State.STATE_1.getSignalChange()));
        sendChangeSignal(correctId2, String.valueOf(State.STATE_1.getSignalChange()));
        //change machine current state from STATE_2 to STATE_2.
        sendChangeSignal(String.valueOf(State.STATE_2.getSignalChange()));
        response = new MockHttpServletResponse();
        sendChangeSignal(correctId2, String.valueOf(State.STATE_2.getSignalChange()));

        assertEquals(Constants.MESSAGE_STATES_COMPLETED, response.getContentAsString());
    }

    private void sendChangeSignal(String signal) throws ServletException, IOException {
        request.setParameter(Constants.REQUEST_SIGNAL, signal);
        request.setParameter(Constants.REQUEST_ID_MACHINE, ID_MACHINE_CORRECT);
        mainServlet.doGet(request, response);
        assertEquals(Constants.RESPONSE_STATUS_OK, response.getStatus());
    }

    private void sendChangeSignal(String id, String signal) throws ServletException, IOException {
        request.setParameter(Constants.REQUEST_SIGNAL, signal);
        request.setParameter(Constants.REQUEST_ID_MACHINE, id);
        mainServlet.doGet(request, response);
        assertEquals(Constants.RESPONSE_STATUS_OK, response.getStatus());
    }


    private int incorrectState(State state) {
        if (state.getSignalChange() == 1) {
            return 0;
        } else {
            return 1;
        }
    }
}
