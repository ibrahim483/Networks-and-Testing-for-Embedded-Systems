package parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;


public class moveBackwardTest {

    Sensor a = new FakeSensor(30, 32, 31, 29, 30);
    Sensor b = new FakeSensor(31, 30, 29, 31, 32);

    Car car = new Car(a,b,new State(0, CarStatus.UNPARKED, new ArrayList<ParkingSpace>()));

    
    /**
     Description: Tests that target the function "moveBackward" of Car.
     Pre-condition: A car at position 0 (beginning of the street), UNPARKED, with an empty
                    detected-spaces list, and fake sensors reporting distances of about 30.
     Post-condition: After each test the car position matches the expected behaviour of moveBackward:
                     the car never moves before the beginning of the street.
     Test-cases:
                 - ifTheCarAtTheBeginningOfTheStreetThanStay
    */

    @Test 
    void ifTheCarAtTheBeginningOfTheStreetThanStay(){
        car.moveBackward();
        assertEquals(car.getState().getPosition(), 0);
    }

    
}
