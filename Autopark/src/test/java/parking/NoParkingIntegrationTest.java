package parking;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NoParkingIntegrationTest {

    @Test
    void reportsNoParkingWhenAllSpacesAreTooSmall() {

        List<ParkingStretch> spaces = List.of(
                new ParkingStretch(50, 1),
                new ParkingStretch(150, 2),
                new ParkingStretch(350, 3)
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

        when(sensorA.getDistance()).thenAnswer(invocation -> {

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
                    distance, distance, distance, distance, distance
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
                    distance, distance, distance, distance, distance
            };
        });

        Car car = new Car(sensorA, sensorB, actuator);

        car.park(new FirstSuitableRoutine());

        assertEquals(CarStatus.NOPARKING, car.getState().getParkStatus());
        assertEquals(500, car.getState().getPosition());
        assertEquals(500, actuator.getPosition());

        verify(actuator, times(500)).moveForward();
        verify(actuator, never()).moveBackward();

        verify(sensorA, times(500)).getDistance();
        verify(sensorB, times(500)).getDistance();
    }
}