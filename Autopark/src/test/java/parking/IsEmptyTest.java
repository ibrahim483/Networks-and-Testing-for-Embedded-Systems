package parking;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class IsEmptyTest {

    @Test
    public void isEmpty_bothSensorsReliable_returnsAverageOfBoth() {
        // Arrange: 5 consistent readings for both sensors
        Sensor sensorA = new FakeSensor(30, 31, 30, 29, 30); // Average is 30
        Sensor sensorB = new FakeSensor(32, 31, 32, 33, 32); // Average is 32
        State initialState = new State(0, CarStatus.UNPARKED, new ArrayList<>());
        Car car = new Car(sensorA, sensorB, initialState);

        // Act
        int distance = car.isEmpty();

        // Assert
        // The expected average of all 10 reliable readings ((150 + 160) / 10) is 31
        assertEquals(31, distance, "Should average both reliable sensors");
    }

    @Test
    public void isEmpty_sensorBUnreliable_returnsAverageOfSensorA() {
        // Arrange: Sensor A is working, Sensor B is unreliable
        Sensor sensorA = new FakeSensor(30, 31, 30, 29, 30); // Average is 30
        Sensor sensorB = new FakeSensor(1000, 1000, 1000, 1000, 1000); // Unreliable readings
        State initialState = new State(0, CarStatus.UNPARKED, new ArrayList<>());
        Car car = new Car(sensorA, sensorB, initialState);

        // Act
        int distance = car.isEmpty();

        // Assert
        assertEquals(30, distance, "Should return average of reliable sensor A");
    } 

@Test
    public void isEmpty_sensorAUnreliable_returnsAverageOfSensorB() {
        // Arrange: Sensor A is unreliable, Sensor B is works
        Sensor sensorA = new FakeSensor(1000, 1000, 1000, 1000, 1000); // Unreliable
        Sensor sensorB = new FakeSensor(32, 31, 32, 33, 32); // Average is 32
        State initialState = new State(0, CarStatus.UNPARKED, new ArrayList<>());
        Car car = new Car(sensorA, sensorB, initialState);

        // Act
        int distance = car.isEmpty();

        // Assert
        assertEquals(32, distance, "Should return average of reliable sensor B");
    }
    }