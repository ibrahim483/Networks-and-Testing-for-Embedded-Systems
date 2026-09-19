package parking;
import parking.Car;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class moveForwardTest {

    Sensor a = new FakeSensor(100, 101, 102, 103, 104);
    Sensor b = new FakeSensor(30, 32, 31, 29, 30);

    Car car = new Car(a,b,new State(0, CarStatus.UNPARKED, new ArrayList<ParkingSpace>()));



    /**
     Description: Calls moveForward once and checks that the car position is increased by one meter.
     Pre-condition: The car is at position 0, UNPARKED, with an empty detected-spaces list.
     Post-condition: The car position is 1.
     Test-cases: moveForward() called once -> getPosition() == 1
    */
    @Test
    void ifTheCarIsAtTheEndOfTheStreetCheckPositionAndChakingIfTheCarPositionIsIncreasing(){
        car.moveForward();
        assertEquals(car.getState().getPosition(), 1);
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
        car.moveForward();
        car.moveForward();
        assertEquals(car.getState().isCurrentTaken(0), false);

    }

    /**
     Description: Moves the car forward 501 times (until the end of the street) and checks that the
                  space at index 500 is not recorded as taken.
     Pre-condition: The car is at position 0, UNPARKED, with an empty detected-spaces list.
     Post-condition: isCurrentTaken(500) is false.
     Test-cases: moveForward() called 501 times (i = 0..500) -> isCurrentTaken(500) == false
    */
    @Test
    void checkAllThePlaceShouldBeEmpty(){
        for(int i = 0; i <= 500; i++){
            car.moveForward();
        }
        assertEquals(car.getState().isCurrentTaken(500), false);

    }

    /**
     Description: Moves the car forward 500 times and checks that the space at index 499 is not
                  recorded as taken.
     Pre-condition: The car is at position 0, UNPARKED, with an empty detected-spaces list.
     Post-condition: isCurrentTaken(499) is false.
     Test-cases: moveForward() called 500 times (i = 0..499) -> isCurrentTaken(499) == false
    */
    @Test
    void checkAllThePlaceShouldAvalibel(){
        for(int i = 0; i < 500; i++){
            car.moveForward();
        }
        assertEquals(car.getState().isCurrentTaken(499), false);

    }



}
