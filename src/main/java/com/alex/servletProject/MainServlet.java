package com.alex.servletProject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * .
 * Date: 12/8/12
 *
 * @autor Alex Rakitsky
 */
public class MainServlet extends HttpServlet {

    private MachineService machineService;

    public MainServlet(MachineService machineService) {
        this.machineService = machineService;
    }

    public MainServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
