package com.alex.servletProject;

import com.alex.servletProject.exceptions.MachineException;
import com.alex.servletProject.exceptions.StateChangeException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;

import static org.easymock.EasyMock.*;
import static org.testng.Assert.assertEquals;

/**
 * Test class for {@link MachineService}.
 * Date: 12/9/12
 *
 * @author Alex Rakitsky
 */
@Test(groups = "unit")
public class MachineServiceTest {

    public static final String ID_1 = "1";
    public static final String ID_2 = "2";
    public static final String CORRECT_SIGNAL = "1";
    private Machine machine_1;

    private Machine machine_2;
    private Map<String,Machine> mockMachineMap;

    private MachineService machineService;

    @BeforeMethod
    public void init(){
        machine_1 = createMock(Machine.class);
        machine_2 = createMock(Machine.class);
        mockMachineMap = new HashMap<String,Machine>(){{
            put(ID_1, machine_1);
            put(ID_2, machine_2);
        }};
        machineService = new MachineService(mockMachineMap);
    }

    @DataProvider(name = "illegalArguments")
    public static Object[][] createIllegalData() {
        return new Object[][]{
                new Object[]{ID_1, null},
                new Object[]{null, CORRECT_SIGNAL},
                new Object[]{ID_1,"blah"},
                new Object[]{"blah",CORRECT_SIGNAL},
                new Object[]{ID_1,"2"},
                new Object[]{ID_1,"-1"},
                new Object[]{"3",CORRECT_SIGNAL},
        };
    }

    @DataProvider(name = "correctArguments")
    public static Object[][] createCorrectData(){
        return new Object[][] {
                new Object[]{ID_1,CORRECT_SIGNAL},
        };
    }

    @Test(expectedExceptions = IllegalArgumentException.class, dataProvider = "illegalArguments")
    public void incorrectInputValue(String machineId, String status) throws MachineException, StateChangeException {
        machineService.setState(machineId, status);
    }

    @Test
    public void testCorrectValues() throws MachineException, StateChangeException {
        String  machineId=ID_1;
        String message = "Test message";
        Machine machine = mockMachineMap.get(machineId);
        expect(machine.nextState()).andReturn(message);
        replay(machine);

        String result = machineService.setState(machineId, CORRECT_SIGNAL);

        assertEquals(message,result);
        verify(machine);
    }

    @Test(expectedExceptions = StateChangeException.class)
    public void testThrowStateChangeException() throws MachineException, StateChangeException {
        String  machineId=ID_1;
        Machine machine = mockMachineMap.get(machineId);
        machine.nextState();
        expectLastCall().andThrow(new StateChangeException("TEST_EXCEPTION"));
        replay(machine);

        machineService.setState(machineId, CORRECT_SIGNAL);

        verify(machine);
    }

    @Test(expectedExceptions = MachineException.class)
    public void testThrowMachineException() throws MachineException, StateChangeException {
        String  machineId=ID_1;
        Machine machine = mockMachineMap.get(machineId);
        machine.nextState();
        expectLastCall().andThrow(new MachineException());
        replay(machine);

        machineService.setState(machineId, CORRECT_SIGNAL);

        verify(machine);
    }

}
