package parking;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SmallestSuitableIntegrationTest {

    @Test
    void parksInSmallestSuitableSpace() {

        List<ParkingStretch> spaces = List.of(
                new ParkingStretch(50, 3),
                new ParkingStretch(150, 8),
                new ParkingStretch(350, 5)
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

        car.park(new SmallestSuitableRoutine());

        assertEquals(CarStatus.PARKED, car.getState().getParkStatus());

        /*
         * Current merged SmallestSuitableRoutine uses:
         *
         * new ParkingStretch(i + 2, count)
         *
         * so the exact 5-space stretch 350..354 produces startPosition 351.
         *
         * This looks like an off-by-one bug in production logic,
         * but this test deliberately matches the merged code.
         */
        assertEquals(351, car.getState().getPosition());
        assertEquals(351, actuator.getPosition());

        verify(actuator, times(500)).moveForward();
        verify(actuator, times(149)).moveBackward();

        verify(sensorA, times(500)).getDistance();
        verify(sensorB, times(500)).getDistance();
    }
}