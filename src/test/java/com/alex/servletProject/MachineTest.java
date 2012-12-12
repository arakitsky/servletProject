package com.alex.servletProject;

import com.alex.servletProject.exceptions.StateChangeException;
import com.alex.servletProject.exceptions.SystemException;
import com.alex.servletProject.reader.IMessageReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * Test for {@link Machine}.
 * Date: 12/10/12
 *
 * @author Alex Rakitsky
 */
public class MachineTest {

    public static final String ID_MACHINE = "1";
    private IMessageReader mockXmlReader;
    private IMessageReader mockDBReader;
    private IMessageReader mockPropertyReader;
    private Machine machine;
    public static final String TEST_MESSAGE = "TestMessage";

    @BeforeTest
    public void init() {
        mockXmlReader = createMock(IMessageReader.class);
        mockDBReader = createMock(IMessageReader.class);
        mockPropertyReader = createMock(IMessageReader.class);
    }

    @BeforeMethod
    public void initMethod() {
        machine = new Machine(ID_MACHINE, mockXmlReader, mockDBReader, mockPropertyReader);
        reset(mockXmlReader);
        reset(mockDBReader);
        reset(mockPropertyReader);
    }

    @DataProvider(name = "correctStateData")
    private Object[][] correctStateData() {
        return new Object[][]{
                new Object[]{State.NONE, State.STATE_1},
                new Object[]{State.STATE_1, State.STATE_2},
                new Object[]{State.STATE_2, State.NONE},
        };
    }

    @DataProvider(name = "exceptionData")
    private Object[][] exceptionData() {
        return new Object[][]{
                new Object[]{State.NONE, mockXmlReader},
                new Object[]{State.STATE_1, mockDBReader},
                new Object[]{State.STATE_2, mockPropertyReader},
        };
    }

    @Test(dataProvider = "exceptionData", expectedExceptions = StateChangeException.class,
            expectedExceptionsMessageRegExp = TEST_MESSAGE)
    public void exceptionTest(State currentState, IMessageReader errorReader) throws SystemException, StateChangeException {
        machine.setCurrentState(currentState);
        expect(errorReader.readMessage(ID_MACHINE)).andReturn(TEST_MESSAGE);
        replay(errorReader);

        machine.nextState(incorrectState(currentState));

        verify(errorReader);
    }

    @Test(dataProvider = "correctStateData")
    public void stateChange(State currentState, State nextState) throws SystemException, StateChangeException {
        machine.setCurrentState(currentState);

        machine.nextState(currentState.getSignalChange());

        assertEquals(nextState, machine.getCurrentState());
    }

    @Test
    public void allCompleted() throws SystemException, StateChangeException {
        State currentState = State.STATE_2;
        machine.setCurrentState(currentState);

        String result = machine.nextState(currentState.getSignalChange());

        assertEquals(Constants.MESSAGE_STATES_COMPLETED, result);
    }

    private int incorrectState(State state) {
        if (state.getSignalChange() == 1) {
            return 0;
        } else {
            return 1;
        }
    }
}
