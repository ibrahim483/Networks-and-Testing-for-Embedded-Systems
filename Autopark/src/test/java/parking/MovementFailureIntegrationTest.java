package parking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovementFailureIntegrationTest {

    
    int simulationPosition;


    @Test
    void failedForwardMovementLeavesCarStateUnchanged() {
        Actuator actuator = mock(Actuator.class);
        Sensor sensorA = mock(Sensor.class);
        Sensor sensorB = mock(Sensor.class);
        Car c = new Car(sensorA, sensorB, actuator);
        when(sensorA.getDistance()).thenReturn(new int[]{250,300,233,222,222});
        when(sensorB.getDistance()).thenReturn(new int[]{-1,-100,-200,-150,-10});

        simulationPosition = c.getState().getPosition();
        when(actuator.getPosition()).thenAnswer(invocation -> simulationPosition);
        when(actuator.moveForward()).thenAnswer(invocation -> {
            if (simulationPosition >= 500) {
                return false;
            }
        
            simulationPosition++;
            return true;
        });

        Car car = new Car(sensorA, sensorB, actuator);

        State result = car.moveForward();

        assertEquals(0, result.getPosition());
        assertEquals(CarStatus.SENSORERROR, result.getParkStatus());
        assertTrue(result.getDetectedSpace().isEmpty());

        verify(actuator).moveForward();
    }
}