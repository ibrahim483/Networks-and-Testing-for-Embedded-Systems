package parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParkTest {

    @Test
    void isEmpty_bothSensorsClean_returnsAverage() {
        Sensor a = new FakeSensor(30, 32, 31, 29, 30);
        Sensor b = new FakeSensor(31, 30, 29, 31, 32);
        Car car = new Car(a, b);

        assertEquals(0, car.isEmpty(), 2);
    }
}