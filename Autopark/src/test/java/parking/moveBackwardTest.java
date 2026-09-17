package parking;
import parking.Car;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;


public class moveBackwardTest {

    Sensor a = new FakeSensor(30, 32, 31, 29, 30);
    Sensor b = new FakeSensor(31, 30, 29, 31, 32);

    Car car = new Car(a,b,new State(0, CarStatus.UNPARKED, new ArrayList<ParkingSpace>()));

    
    /*
    Those are the testings that targets the function "MoveBackward".
    */

    @Test 
    void ifTheCarAtTheBeginningOfTheStreetThanStay(){
        car.moveBackward();
        assertEquals(car.getState().getPosition(), 0);
    }

    
}
