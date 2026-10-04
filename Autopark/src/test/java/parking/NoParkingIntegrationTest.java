package parking;


import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.delegatesTo;

public class NoParkingIntegrationTest {

    @Test
    void reportsNoParkingWhenAllSpacesAreTooSmall() {
        List<ParkingStretch> spaces = List.of(
                new ParkingStretch(50, 1),
                new ParkingStretch(150, 2),
                new ParkingStretch(350, 3)
        );

        Actuator actuator = mock(
                Actuator.class,
                delegatesTo(new SimulatedActuator())
        );

        Sensor sensorA = mock(
                Sensor.class,
                delegatesTo(new StreetSensor(actuator, spaces, 250))
        );

        Sensor sensorB = mock(
                Sensor.class,
                delegatesTo(new StreetSensor(actuator, spaces, -1))
        );

        Car car = new Car(sensorA, sensorB, actuator);
        FirstSuitableRoutine routine = new FirstSuitableRoutine(car);

        assertFalse(routine.execute());
        assertEquals(CarStatus.NOPARKING, car.getState().getParkStatus());
        assertEquals(500, car.getState().getPosition());
        assertEquals(500, actuator.getPosition());

        verify(actuator, times(500)).moveForward();
        verify(actuator, never()).moveBackward();
        verify(sensorA, times(500)).getDistance();
        verify(sensorB, times(500)).getDistance();
        System.out.println(car.getState().getPosition());
    }
}
