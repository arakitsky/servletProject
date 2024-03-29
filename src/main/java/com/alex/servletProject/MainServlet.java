package com.alex.servletProject;

import com.alex.servletProject.exceptions.StateChangeException;
import com.alex.servletProject.exceptions.SystemException;
import com.alex.servletProject.reader.MachineDAO;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
    public MainServlet() throws SystemException, SQLException, ClassNotFoundException {
        machineService = new MachineService();

        //for correct work program init mock db
        initDB();
    }

    /**
     * Insert values in mock database.
     *
     * @throws SystemException
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void initDB() throws SystemException, SQLException, ClassNotFoundException {
        MachineDAO machineDAO = MachineDAO.getInstance();
        machineDAO.createTable();
        machineDAO.addMachineError("1", "Machine error message 1");
        machineDAO.addMachineError("2", "Machine error message 2");
        machineDAO.addMachineError("3", "Machine error message 3");
        machineDAO.addMachineError("4", "Machine error message 4");
        machineDAO.addMachineError("5", "Machine error message 5");
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
            resp.setStatus(Constants.RESPONSE_STATUS_OK);
        } catch (IllegalArgumentException e) {
            LOG.warn(e);

            resp.setStatus(Constants.RESPONSE_STATUS_BED);
        } catch (SystemException e) {
            LOG.error(e);
            out.write(Constants.MESSAGE_SYSTEM_ERROR);
            resp.setStatus(Constants.RESPONSE_STATUS_SERVER_ERROR);
        } catch (StateChangeException e) {
            LOG.info(e);
            out.print(e.getMessage());
            resp.setStatus(Constants.RESPONSE_STATUS_OK);
        }

    }
}
