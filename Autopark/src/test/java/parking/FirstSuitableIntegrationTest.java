package parking;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class FirstSuitableIntegrationTest {

    int actuatorPosition;
    Sensor sensorA = mock(Sensor.class);
    Sensor sensorB = mock(Sensor.class);
    Actuator actuator = mock(Actuator.class);
    Car car = new Car(sensorA, sensorB, actuator);
    ArrayList<ParkingSpace> pSpace = new ArrayList<>();

    @Test
    void parksInFirstSuitableSpace() {

        for (int i = 0; i < car.getStreetLength(); i++) {
            if (i < 53 && i >= 50) {
                pSpace.add(new ParkingSpace(i, false));
            }else if (i < 157 && i >= 150) {
                pSpace.add(new ParkingSpace(i, false));
            }else if (i < 354 && i > 350) {
                pSpace.add(new ParkingSpace(i, false));
            }else{pSpace.add(new ParkingSpace(i, true));}
        }

       actuatorPosition = 0;

        when(actuator.getPosition())
                .thenAnswer(invocation -> actuatorPosition);

        when(actuator.moveForward()).thenAnswer(invocation -> {
            if (actuatorPosition >= car.getStreetLength()) {
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


        assertEquals(CarStatus.PARKED, car.getState().getParkStatus());
        assertEquals(158, car.getState().getPosition());
        assertEquals(158, actuator.getPosition());

        verify(actuator, times(500)).moveForward();
        verify(actuator, times(342)).moveBackward();
        verify(sensorA, times(500)).getDistance();
        verify(sensorB, times(500)).getDistance();

        car.unPark();
        assertEquals(CarStatus.UNPARKED, car.getState().getParkStatus());

        for (int position = 158; position < car.getStreetLength(); position++) {
            car.moveForward();
        }

        assertEquals(500, car.getState().getPosition());
        assertEquals(500, actuator.getPosition());
        assertEquals(CarStatus.UNPARKED, car.getState().getParkStatus());
    }
}
