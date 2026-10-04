package parking;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class FirstSuitableIntegrationTest {

    int actuatorPosition;
    @Test
    void parksInFirstSuitableSpace() {
        List<ParkingStretch> spaces = List.of(
                new ParkingStretch(50, 3),
                new ParkingStretch(150, 8),
                new ParkingStretch(350, 5)
        );

        Actuator actuator = mock(Actuator.class);
       actuatorPosition = 0;

        when(actuator.getPosition())
                .thenAnswer(invocation -> actuatorPosition);

        when(actuator.moveForward()).thenAnswer(invocation -> {
            if (actuatorPosition >= Car.STREET_LENGTH) {
                return false;
            }
            actuatorPosition++;
            return true;
        });

        when(actuator.moveBackward()).thenAnswer(invocation -> {
            if (actuatorPosition <= 0) {
                return false;
            }
            actuatorPosition--;
            return true;
        });
        Sensor sensorA = mock(Sensor.class);
        boolean[] sensorABroken = {false};

        when(sensorA.getDistance()).thenAnswer(invocation -> {
            int position = actuator.getPosition();

            if (position >= 250) {
                sensorABroken[0] = true;
            }

            if (sensorABroken[0]) {
                return new int[] {1000, 1000, 1000, 1000, 1000};
            }

            int cell = Math.max(0, position - 1);
            int distance = 30;

            for (ParkingStretch space : spaces) {
                if (cell >= space.getStartPosition()
                        && cell < space.getEndPosition()) {
                    distance = 150;
                    break;
                }
            }

            return new int[] {
                    distance - 2, distance - 1, distance,
                    distance + 1, distance + 2
            };
        });

        Sensor sensorB = mock(Sensor.class);

        when(sensorB.getDistance()).thenAnswer(invocation -> {
            int cell = Math.max(0, actuator.getPosition() - 1);
            int distance = 30;

            for (ParkingStretch space : spaces) {
                if (cell >= space.getStartPosition()
                        && cell < space.getEndPosition()) {
                    distance = 150;
                    break;
                }
            }

            return new int[] {
                    distance - 2, distance - 1, distance,
                    distance + 1, distance + 2
            };
        });
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
