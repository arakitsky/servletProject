package com.alex.servletProject;

import java.io.File;

/**
 * All constants for project.
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
public final class Constants {

    /**
     * Response status, set with no errors
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">Status code definitions</a>
     */
    public static final int RESPONSE_STATUS_OK = 200;
    /**
     * Response status, set the invalid input parameters.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">Status code definitions</a>
     */
    public static final int RESPONSE_STATUS_BED = 400;
    /**
     * Response status, set by internal application error.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">Status code definitions</a>
     */
    public static final int RESPONSE_STATUS_SERVER_ERROR = 500;

    /**
     * http request params for machine id.
     */
    public static final String REQUEST_ID_MACHINE = "id";
    /**
     * http request params for signal.
     */
    public static final String REQUEST_SIGNAL = "signal";

    /**
     * Message passing all states.
     */
    public static final String MESSAGE_STATES_COMPLETED = "All ok";
    /**
     * Unexpected system error client message
     */
    public static final String MESSAGE_SYSTEM_ERROR = "Sorry an unexpected error";

    /**
     * Jdbc driver name.
     */
    public static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";
    /**
     * Jdbc database name.
     */
    public static final String JDBC_DB_NAME = "machineMock";
    /**
     * Jdbc name of database user.
     */
    public static final String JDBS_USER = "su";
    /**
     * Jdbc password of database user.
     */
    public static final String JDBC_PASSWORD = "";
    /**
     * Jdbc url.
     */
    public static final String JDBC_URL = "jdbc:hsqldb:";

    /**
     * Unique identifier in the file properties of the message machine.
     */
    public static final String PROP_MACHINE_ID = "machine";

    /**
     * Path to file xml with machine error messages.
     * Used on {@link com.alex.servletProject.reader.XmlMessageReader}.
     */
    public static final String PATH_MESSAGE_FILE_XML = File.separator + "messages.xml";

    /**
     * Path to file property with machine error messages.
     * Used on {@link com.alex.servletProject.reader.PropertyMessageReader}
     */
    public static final String PATH_MESSAGE_FILE_PROPERTY = File.separator + "messages.properties";
}
