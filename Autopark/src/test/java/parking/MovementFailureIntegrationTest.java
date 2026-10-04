package parking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovementFailureIntegrationTest {

    @Test
    void stopsWhenActuatorCannotMoveForward() {
        Actuator actuator = mock(Actuator.class);
        Sensor sensorA = mock(Sensor.class);
        Sensor sensorB = mock(Sensor.class);

        when(actuator.getPosition()).thenReturn(0);
        when(actuator.moveForward()).thenReturn(false);

        Car car = new Car(sensorA, sensorB, actuator);
        SmallestSuitableRoutine routine =
                new SmallestSuitableRoutine(car);

        assertFalse(routine.execute());
        assertEquals(0, car.getState().getPosition());
        assertEquals(0, actuator.getPosition());
        assertEquals(CarStatus.UNPARKED, car.getState().getParkStatus());
        assertTrue(car.getState().getDetectedSpace().isEmpty());

        verify(actuator).moveForward();
        verify(actuator, never()).moveBackward();
        verifyNoInteractions(sensorA, sensorB);
    }
}