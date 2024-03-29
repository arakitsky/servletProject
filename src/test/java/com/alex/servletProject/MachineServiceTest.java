package com.alex.servletProject;

import com.alex.servletProject.exceptions.StateChangeException;
import com.alex.servletProject.exceptions.SystemException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.*;

/**
 * Test class for {@link MachineService}.
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
public class MachineServiceTest {

    public static final String CORRECT_ID = "1";
    public static final String ID_2 = "2";
    public static final String CORRECT_SIGNAL = "1";
    public static final int CORRECT_ID_INT = 1;

    private Machine machine_1;
    private Machine machine_2;
    private Map<String, Machine> mockMachineMap;
    private MachineFactory mockMachineFactory;

    private MachineService machineService;

    @BeforeMethod
    public void init() {
        machine_1 = createMock(Machine.class);
        machine_2 = createMock(Machine.class);
        mockMachineMap = new HashMap<String, Machine>() {{
            put(CORRECT_ID, machine_1);
            put(ID_2, machine_2);
        }};
        mockMachineFactory = createMock(MachineFactory.class);
        machineService = new MachineService(mockMachineMap, mockMachineFactory);
    }

    @DataProvider(name = "illegalArguments")
    public static Object[][] createIllegalData() {
        return new Object[][]{
                new Object[]{CORRECT_ID, null},
                new Object[]{null, CORRECT_SIGNAL},
                new Object[]{CORRECT_ID, "blah"},
                new Object[]{CORRECT_ID, "2"},
                new Object[]{CORRECT_ID, "-1"},
        };
    }

    @DataProvider(name = "correctArguments")
    public static Object[][] createCorrectData() {
        return new Object[][]{
                new Object[]{CORRECT_ID, CORRECT_SIGNAL},
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "illegalArguments")
    public void incorrectInputValue(String machineId, String status) throws SystemException, StateChangeException {
        machineService.setState(machineId, status);
    }

    @Test
    public void testCorrectValues() throws SystemException, StateChangeException {
        String machineId = CORRECT_ID;
        String message = "Test message";
        Machine machine = mockMachineMap.get(machineId);
        expect(machine.nextState(CORRECT_ID_INT)).andReturn(message);
        replay(machine);

        String result = machineService.setState(machineId, CORRECT_SIGNAL);

        assertEquals(message, result);
        verify(machine);
    }

    @Test(expectedExceptions = StateChangeException.class)
    public void testThrowStateChangeException() throws SystemException, StateChangeException {
        String machineId = CORRECT_ID;
        Machine machine = mockMachineMap.get(machineId);
        machine.nextState(CORRECT_ID_INT);
        expectLastCall().andThrow(new StateChangeException("TEST_EXCEPTION"));
        replay(machine);

        machineService.setState(machineId, CORRECT_SIGNAL);

        verify(machine);
    }

    @Test(expectedExceptions = SystemException.class)
    public void testThrowMachineException() throws SystemException, StateChangeException {
        String machineId = CORRECT_ID;
        Machine machine = mockMachineMap.get(machineId);
        machine.nextState(CORRECT_ID_INT);
        expectLastCall().andThrow(new SystemException());
        replay(machine);

        machineService.setState(machineId, CORRECT_SIGNAL);

        verify(machine);
    }

    @Test
    public void testNotValidSignalNotAddMachine() throws SystemException, StateChangeException {
        String exclusiveId = "100500";
        int incorrectSignal = 1;
        expect(mockMachineFactory.createMachine(exclusiveId)).andReturn(machine_1);
        expect(machine_1.nextState(incorrectSignal)).andThrow(new StateChangeException("Test state change exception"));
        replay(mockMachineFactory, machine_1);


        try {
            machineService.setState(exclusiveId, String.valueOf(incorrectSignal));
        } catch (StateChangeException e) {
        }

        assertFalse(machineService.getMachineMap().containsKey(exclusiveId));
    }

    @Test
    public void testValidSignalNotAddMachine() throws SystemException, StateChangeException {
        String exclusiveId = "100500";
        int correctSignal = 1;
        expect(mockMachineFactory.createMachine(exclusiveId)).andReturn(machine_1);
        expect(machine_1.nextState(correctSignal)).andReturn("");
        replay(mockMachineFactory, machine_1);


        machineService.setState(exclusiveId, String.valueOf(correctSignal));

        assertTrue(machineService.getMachineMap().containsKey(exclusiveId));
    }

}
