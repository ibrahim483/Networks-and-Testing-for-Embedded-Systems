package parking;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.Mockito.*;

public class SmallestSuitableIntegrationTest {
    @Test
    void parksInSmallestSuitableSpace() {
        List<ParkingStretch> spaces = List.of(
                new ParkingStretch(50, 3),
                new ParkingStretch(150, 8),
                new ParkingStretch(350, 5)
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

        SmallestSuitableRoutine routine = new SmallestSuitableRoutine(car);

        assertTrue(routine.execute());
        assertEquals(CarStatus.PARKED, car.getState().getParkStatus());
        assertEquals(355, car.getState().getPosition());
        assertEquals(355, actuator.getPosition());

        verify(actuator, times(500)).moveForward();
        verify(actuator, times(145)).moveBackward();
        verify(sensorA, times(500)).getDistance();
        verify(sensorB, times(500)).getDistance();

        car.unPark();
        assertEquals(CarStatus.UNPARKED, car.getState().getParkStatus());

        for (int position = 355; position < Car.STREET_LENGTH; position++) {
            car.moveForward();
        }

        assertEquals(500, car.getState().getPosition());
        assertEquals(500, actuator.getPosition());
        assertEquals(CarStatus.UNPARKED, car.getState().getParkStatus());


    }
}
