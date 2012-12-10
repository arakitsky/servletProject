package com.alex.servletProject;

import com.alex.servletProject.exceptions.SystemException;
import com.alex.servletProject.exceptions.StateChangeException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * </>Servlet to test the signal sequence.
 * Siglaly transmitted in a GET request in the format
 * <i><[url to servlet]? Signal = (message) & = (number of state machines)</i>.
 * The signal is processed {@link MachineService}.
 * Date: 12/8/12
 *
 * @author Alex Rakitsky
 */
public class MainServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(MainServlet.class);

    private MachineService machineService;

    /**
     * Constructor that sets the signal handler for machines.
     *
     * @param machineService signal handler for machines.
     */
    public MainServlet(MachineService machineService) {
        this.machineService = machineService;
    }

    /**
     * Constructor with not param. That initializes all the facilities required for the application.
     */
    public MainServlet() {
        Map<String, Machine> machineMap = new HashMap<String,Machine>(){{
            put("1",new Machine("1",null,null,null));
            put("2",new Machine("2",null,null,null));
            put("3",new Machine("3",null,null,null));
            put("4",new Machine("4",null,null,null));
            put("5",new Machine("5",null,null,null));
        }};
        machineService = new MachineService(machineMap);
    }


    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a GET request.
     * Reads from the request parameters {@link Constants#REQUEST_ID_MACHINE} and {@link Constants#REQUEST_SIGNAL},
     * and processes them {@link MachineService}.
     *
     * @param req  httpServletRequest
     * @param resp httpServletResponse
     * @throws ServletException see {@link HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * @throws IOException      see {@link HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}
     * @see HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/plain");
        String signal = req.getParameter(Constants.REQUEST_SIGNAL);
        String id = req.getParameter(Constants.REQUEST_ID_MACHINE);
        LOG.debug("Main request signal :: " + signal + "; machine id :: " + id);

        try {

            String response = machineService.setState(id, signal);

            LOG.debug("Response :: " + response);
            out.print(response);
            resp.setStatus(Constants.RESPONSE_OK);
        } catch (IllegalArgumentException e) {
            LOG.warn(e);
            resp.setStatus(Constants.RESPONSE_BED);
        } catch (SystemException e) {
            LOG.warn(e);
            resp.setStatus(Constants.RESPONSE_SERVER_ERROR);
        } catch (StateChangeException e) {
            LOG.info(e);
            out.print(e.getMessage());
            resp.setStatus(Constants.RESPONSE_OK);
        }

    }
}
