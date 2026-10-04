package parking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovementFailureIntegrationTest {

    @Test
    void failedForwardMovementLeavesCarStateUnchanged() {

        Actuator actuator = mock(Actuator.class);
        Sensor sensorA = mock(Sensor.class);
        Sensor sensorB = mock(Sensor.class);

        when(actuator.getPosition()).thenReturn(0);
        when(actuator.moveForward()).thenReturn(false);

        Car car = new Car(sensorA, sensorB, actuator);

        State result = car.moveForward();

        assertEquals(0, result.getPosition());
        assertEquals(CarStatus.UNPARKED, result.getParkStatus());
        assertTrue(result.getDetectedSpace().isEmpty());

        verify(actuator).moveForward();
        verify(actuator, never()).getPosition();
        verifyNoInteractions(sensorA, sensorB);
    }
}