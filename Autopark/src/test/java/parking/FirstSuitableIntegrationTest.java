package parking;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FirstSuitableIntegrationTest {

    @Test
    void parksInFirstSuitableSpace() {

        List<ParkingStretch> spaces = List.of(
                new ParkingStretch(50, 3),   // too small
                new ParkingStretch(150, 8),  // suitable
                new ParkingStretch(350, 5)   // first found when scanning backwards
        );

        Actuator actuator = mock(Actuator.class);
        Sensor sensorA = mock(Sensor.class);
        Sensor sensorB = mock(Sensor.class);

        int[] actuatorPosition = {0};

        when(actuator.getPosition())
                .thenAnswer(invocation -> actuatorPosition[0]);

        when(actuator.moveForward()).thenAnswer(invocation -> {
            if (actuatorPosition[0] >= 500) {
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

        boolean[] sensorABroken = {false};

        when(sensorA.getDistance()).thenAnswer(invocation -> {

            int position = actuator.getPosition();

            if (position >= 250) {
                sensorABroken[0] = true;
            }

            if (sensorABroken[0]) {
                return new int[]{1000, 1000, 1000, 1000, 1000};
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

            return new int[]{
                    distance - 2,
                    distance - 1,
                    distance,
                    distance + 1,
                    distance + 2
            };
        });

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

            return new int[]{
                    distance - 2,
                    distance - 1,
                    distance,
                    distance + 1,
                    distance + 2
            };
        });

        Car car = new Car(sensorA, sensorB, actuator);

        car.park(new FirstSuitableRoutine());

        assertEquals(CarStatus.PARKED, car.getState().getParkStatus());

        // FirstSuitableRoutine searches from the END of the detected list.
        assertEquals(350, car.getState().getPosition());
        assertEquals(350, actuator.getPosition());

        verify(actuator, times(500)).moveForward();
        verify(actuator, times(150)).moveBackward();

        verify(sensorA, times(500)).getDistance();
        verify(sensorB, times(500)).getDistance();

        car.unPark();

        assertEquals(CarStatus.UNPARKED, car.getState().getParkStatus());

        for (int position = 350; position < car.getStreetLength(); position++) {
            car.moveForward();
        }

        assertEquals(500, car.getState().getPosition());
        assertEquals(500, actuator.getPosition());
    }
}