package com.alex.servletProject;

import com.alex.servletProject.exceptions.StateChangeException;
import com.alex.servletProject.exceptions.SystemException;
import com.alex.servletProject.reader.DbMessageReader;
import com.alex.servletProject.reader.IMessageReader;
import com.alex.servletProject.reader.PropertyMessageReader;
import com.alex.servletProject.reader.XmlMessageReader;
import org.apache.log4j.Logger;

import static com.alex.servletProject.State.*;

/**
 * The state machine. Has a unique ID number and status.
 * Status is changed to the following rule: {@link State#NONE} -> {@link State#STATE_1} -> {@link State#STATE_2}
 * When the {@link State#STATE_2} is complete - display and set the state {@link State#NONE}
 * <p/>
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
public class Machine {

    public Logger LOG = Logger.getLogger(Machine.class);

    private final String id;

    private State currentState = NONE;

    private IMessageReader noneErrorReader;
    private IMessageReader state1ErrorReader;
    private IMessageReader state2ErrorReader;


    /**
     * The constructor initializes the default field values.
     *
     * @param id unique id name
     */
    public Machine(String id) throws SystemException {
        String pathToXml = getClass().getResource(Constants.PATH_MESSAGE_FILE_XML).toString();
        String pathProperty = getClass().getResource(Constants.PATH_MESSAGE_FILE_PROPERTY).toString();
        LOG.debug("Path to xml message file :: " + pathToXml);
        LOG.debug("Path to property message file :: " + pathProperty);
        noneErrorReader = new XmlMessageReader(pathToXml);
        state1ErrorReader = new DbMessageReader();
        state2ErrorReader = new PropertyMessageReader(pathProperty);
        this.id = id;
    }

    /**
     * Constructor state machine. Has a unique ID number and status.
     * Status is changed to the following rule: {@link State#NONE} -> {@link State#STATE_1} -> {@link State#STATE_2}
     * When the {@link State#STATE_2} is complete - display and set the state {@link State#NONE}
     *
     * @param id                unique id name
     * @param noneErrorReader   source from which to read the error message when an error switching to {@link State#STATE_1}
     * @param state1ErrorReader source from which to read the error message when an error switching to {@link State#STATE_2}
     * @param state2ErrorReader source from which to read the error
     *                          message when an error switching from {@link State#STATE_2} to {@link State#NONE}
     */
    public Machine(String id, IMessageReader noneErrorReader, IMessageReader state1ErrorReader, IMessageReader state2ErrorReader) {
        this.id = id;
        this.noneErrorReader = noneErrorReader;
        this.state1ErrorReader = state1ErrorReader;
        this.state2ErrorReader = state2ErrorReader;
    }

    /**
     * Сhanges the status to the next. Status is changed to the following rule:
     * {@link State#NONE} -> {@link State#STATE_1} -> {@link State#STATE_2}.
     * When the {@link State#STATE_2} is complete - display and set the state {@link State#NONE}
     * If the signal was wrong to call {@link StateChangeException}
     *
     * @param signal signal status changes
     * @return message if all states had passed, not passed - empty line.
     * @throws com.alex.servletProject.exceptions.SystemException
     *                              an error of application (error сonnect
     *                              to your database, the properties file is not found, etc.)
     * @throws StateChangeException mismatch at the input to the signal necessary to change
     */
    public String nextState(int signal) throws SystemException, StateChangeException {
        String result = "";
        switch (getCurrentState()) {
            case NONE:
                changeState(signal, noneErrorReader, STATE_1);
                break;
            case STATE_1:
                changeState(signal, state1ErrorReader, STATE_2);
                break;
            case STATE_2:
                changeState(signal, state2ErrorReader, NONE);
                result = Constants.MESSAGE_STATES_COMPLETED;
                break;

        }
        return result;
    }

    /**
     * Сhanges the current status to the next. Сhecks the input signal with the necessary.
     * If the signal is incorrect - throws {@link StateChangeException}
     *
     * @param signal      input signal
     * @param errorReader source from which to read the error message
     * @param nextState   next state
     * @throws com.alex.servletProject.exceptions.SystemException
     *                              an error of application (error сonnect
     *                              to your database, the properties file is not found, etc.)
     * @throws StateChangeException mismatch at the input to the signal necessary to change
     */
    private void changeState(int signal, IMessageReader errorReader, State nextState) throws StateChangeException, SystemException {
        if (getCurrentState().getSignalChange() != signal) {
            String message = errorReader.readMessage(id);
            LOG.info("Incorrect input state for machine " + id + " - state not change read error message :: " + message);
            throw new StateChangeException(message);
        }
        LOG.info("State machine id = " + id + " has changed from " + getCurrentState() + " to " + nextState);
        setCurrentState(nextState);
    }

    /**
     * Get current state.
     *
     * @return current state of machine
     */
    State getCurrentState() {
        return currentState;
    }

    /**
     * Set current state of machine.
     */
    void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}
