package com.alex.servletProject;

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
    public static final int RESPONSE_OK = 200;
    /**
     * Response status, set the invalid input parameters.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">Status code definitions</a>
     */
    public static final int RESPONSE_BED = 400;
    /**
     * Response status, set by internal application error.
     *
     * @see <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html">Status code definitions</a>
     */
    public static final int RESPONSE_SERVER_ERROR = 500;

    /**
     * http request params for machine id
     */
    public static final String REQUEST_ID_MACHINE = "id";
    /**
     * http request params for signal
     */
    public static final String REQUEST_SIGNAL = "signal";

    /**
     * Message passing all states
     */
    public static final String MESSAGE_STATES_COMPLETED = "All ok";
}
