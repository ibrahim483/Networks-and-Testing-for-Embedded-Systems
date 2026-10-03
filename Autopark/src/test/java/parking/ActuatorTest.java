package parking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActuatorTest {
    
    Actuator actuator;
    Sensor sensorA;
    Sensor sensorB;
    ArrayList<ParkingSpace> pSpaces;
    int simulationPosition;
    State simulationState;
    Car c;

    @BeforeEach 
    public void setup(){

        actuator = mock(Actuator.class);
        sensorA = mock(Sensor.class);
        sensorB = mock(Sensor.class);
        pSpaces = new ArrayList<>();
        simulationState = new State(500, CarStatus.UNPARKED, pSpaces);
        c = new Car(sensorA, sensorB, simulationState, actuator);

        simulationPosition = c.getState().getPosition();
        when(actuator.getPosition()).thenAnswer(invocation -> simulationPosition);
        when(actuator.moveForward()).thenAnswer(invocation -> {
            if (simulationPosition >= 500) {
                return false;
            }
        
            simulationPosition++;
            return true;
        });

        when(actuator.moveBackward()).thenAnswer(invocation -> {
            if (simulationPosition <= 0) {
                return  false;
            }
            simulationPosition--;
            return true;
        });

        when(sensorA.getDistance()).thenReturn(new int[]{100,100,100,100,100});
        when(sensorB.getDistance()).thenReturn(new int[]{100,100,100,100,100});

    }

    @Test
    public void TestToMoveAfter500Meters(){
        
        boolean check = actuator.moveForward();
        assertFalse(check);
    }

    @Test
    public void moveTheCarForwardAndSetItsPosition() {
        simulationPosition = 10;
        c.setState(new State(
            simulationPosition, CarStatus.UNPARKED, pSpaces
        ));

        c.moveForward();

        assertEquals(11, simulationPosition);
        assertEquals(11, c.getState().getPosition());

        verify(actuator).moveForward();
        verify(actuator).getPosition();
    }

    @Test 
    public void rejectMovementLeaveStateUnchanged(){
        simulationPosition = 100;
        simulationState.setPosition(simulationPosition);
        c.setState(simulationState);

        doReturn(false).when(actuator).moveForward();
        State result = c.moveForward();

        assertEquals(100, result.getPosition());
        assertEquals(100, simulationPosition);
        assertTrue(result.getDetectedSpace().isEmpty());

        verify(actuator).moveForward();
        verifyNoInteractions(sensorA, sensorB);
    }

    @Test 
    public void carShouldntDrivePastZero(){

        simulationPosition = 0;
        simulationState.setPosition(simulationPosition);
        c.setState(simulationState);

        State result = c.moveBackward();

        assertEquals(0, result.getPosition());
        assertEquals(0, simulationPosition);

        verify(actuator).moveBackward();
    }

    @Test
    public void moveTheCarBackwardsUsingActuatorMock() {
        simulationPosition = 10;
        simulationState.setPosition(simulationPosition);
        c.setState(simulationState);

        State result = c.moveBackward();

        assertEquals(9, result.getPosition());
        assertEquals(9, simulationPosition);

        verify(actuator).moveBackward();
        verify(actuator).getPosition();
    }
}
