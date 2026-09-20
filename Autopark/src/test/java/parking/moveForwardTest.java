package parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class moveForwardTest {





    /**
     Description: Calls moveForward once and checks that the car position is increased by one meter.
     Pre-condition: The car is at position 0, UNPARKED, with an empty detected-spaces list.
     Post-condition: The car position is 1.
     Test-cases: moveForward() called once -> getPosition() == 1
    */

    @Test 
    void ifTheCarIsAtTheEndOfTheStreetCheckPositionAndChakingIfTheCarPositionIsIncreasing(){

        Sensor a = new FakeSensor(30, 32, 31, 29, 30);
        Sensor b = new FakeSensor(30, 32, 31, 29, 30);

        Car car = new Car(a,b,new State(0, CarStatus.UNPARKED, new ArrayList<ParkingSpace>()));

        car.moveForward();
        assertEquals(1, car.getState().getPosition());
    }


    /**
     Description: Moves the car forward twice and checks that the space at index 0 of the detected
                  spaces is not recorded as taken.
     Pre-condition: The car is at position 0, UNPARKED, with an empty detected-spaces list.
     Post-condition: Two ParkingSpace entries were added and isCurrentTaken(0) is false.
     Test-cases: moveForward() called twice -> isCurrentTaken(0) == false
    */
    @Test
    void checkTheNextPlaceShouldBeEmpty(){

    Sensor a = new FakeSensor(150, 150, 150, 150, 150);
    Sensor b = new FakeSensor(150, 150, 150, 150, 150);

    Car car = new Car(a,b,new State(0, CarStatus.UNPARKED, new ArrayList<ParkingSpace>()));
        car.moveForward();
        car.moveForward();
        assertEquals(false, car.getState().isCurrentTaken(0));

    }

    /**
     Description: Moves the car forward 500 times (until the end of the street) and checks that the
                  last recorded space (index 499) is recorded as taken.
     Pre-condition: The car is at position 0, UNPARKED, with an empty detected-spaces list.
     Post-condition: isCurrentTaken(499) is true.
     Test-cases: moveForward() called 500 times (i = 0..499) -> isCurrentTaken(499) == true
    */
    @Test
    void checkAllThePlaceShouldBeEmpty(){

    Sensor a = new FakeSensor(30, 32, 31, 29, 30);
    Sensor b = new FakeSensor(30, 32, 31, 29, 30);

    Car car = new Car(a,b,new State(0, CarStatus.UNPARKED, new ArrayList<ParkingSpace>()));
        for(int i = 0; i <= 500; i++){
            car.moveForward();
        }
        assertEquals(car.getState().isCurrentTaken(50), true);

    }

    /**
     Description: Moves the car forward 500 times with sensors reading 150 (free) and checks that the
                  space at index 499 is not recorded as taken.
     Pre-condition: The car is at position 0, UNPARKED, with an empty detected-spaces list.
     Post-condition: isCurrentTaken(499) is false.
     Test-cases: moveForward() called 500 times (i = 0..499) -> isCurrentTaken(499) == false
    */
    @Test
    void checkAllThePlaceShouldAvalibel(){

    Sensor a = new FakeSensor(150, 150, 150, 150, 150);
    Sensor b = new FakeSensor(150, 150, 150, 150, 150);

    Car car = new Car(a,b,new State(0, CarStatus.UNPARKED, new ArrayList<ParkingSpace>()));
        for(int i = 0; i < 500; i++){
            car.moveForward();
        }
        assertEquals(false, car.getState().isCurrentTaken(499));

    }



}
