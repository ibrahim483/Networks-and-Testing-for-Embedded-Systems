package parking;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.Mockito.*;

public class FirstSuitableIntegrationTest {
    @Test
    void parksInFirstSuitableSpace() {
        List<ParkingStretch> spaces = List.of(
                new ParkingStretch(50, 3),
                new ParkingStretch(150, 8),
                new ParkingStretch(350, 5)
        );

        Actuator actuator = mock(Actuator.class);
        int[] actuatorPosition = {0};

        when(actuator.getPosition())
                .thenAnswer(invocation -> actuatorPosition[0]);

        when(actuator.moveForward()).thenAnswer(invocation -> {
            if (actuatorPosition[0] >= Car.STREET_LENGTH) {
                return false;
            }
            actuatorPosition[0]++;
            return true;
        });

        when(actuator.moveBackward()).thenAnswer(invocation -> {
            if (actuatorPosition[0] <= 0) {
                return false;
            }
            actuatorPosition[0]--;
            return true;
        });
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

        assertTrue(routine.execute());
        assertEquals(CarStatus.PARKED, car.getState().getParkStatus());
        assertEquals(158, car.getState().getPosition());
        assertEquals(158, actuator.getPosition());

        verify(actuator, times(500)).moveForward();
        verify(actuator, times(342)).moveBackward();
        verify(sensorA, times(500)).getDistance();
        verify(sensorB, times(500)).getDistance();

        car.unPark();
        assertEquals(CarStatus.UNPARKED, car.getState().getParkStatus());

        for (int position = 158; position < Car.STREET_LENGTH; position++) {
            car.moveForward();
        }

        assertEquals(500, car.getState().getPosition());
        assertEquals(500, actuator.getPosition());
        assertEquals(CarStatus.UNPARKED, car.getState().getParkStatus());
    }
}
