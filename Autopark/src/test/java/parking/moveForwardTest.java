package parking;
import parking.Car;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

public class moveForwardTest {

    Sensor a = new FakeSensor(100, 101, 102, 103, 104);
    Sensor b = new FakeSensor(30, 32, 31, 29, 30);

    Car car = new Car(a,b,new State(0, CarStatus.UNPARKED, new ArrayList<ParkingSpace>()));


    
    /*
    Those are the testings that targets the function "MoveForward".
    */

    @Test
    void ifTheCarIsAtTheEndOfTheStreetCheckPositionAndChakingIfTheCarPositionIsIncreasing(){
        car.moveForward();
        assertEquals(car.getState().getPosition(), 1);
    }


    @Test
    void checkTheNextPlaceShouldBeEmpty(){
        car.moveForward();
        car.moveForward();
        assertEquals(car.getState().isCurrentTaken(0), false);

    }

        @Test
    void checkAllThePlaceShouldBeEmpty(){
        for(int i = 0; i <= 500; i++){
            car.moveForward();
        }
        assertEquals(car.getState().isCurrentTaken(500), false);

    }

            @Test
    void checkAllThePlaceShouldAvalibel(){
        for(int i = 0; i < 500; i++){
            car.moveForward();
        }
        assertEquals(car.getState().isCurrentTaken(499), false);

    }


    @Test 
    void ifTheCarIsAtTheendOfTheStreetDontMoveSendSignalLimitReached(){

    }

    @Test 
    void ifTheCarIsNotEndOfTheStreetAndNoEmptySpotAndNotParkedThenMoveForwardOneMeterAndClearWorkList(){

    }

    @Test 
    void ifTheCarIsNotEndOfTheStreetAndNotParkedAndTheListIsNotFUllButThereIsAnEmptySpotThenMoveForwardOneMeterAndAddPositionToTheList(){

    }

    @Test 
    void ifTheCarIsNotParkedOrEndOfTheStreetButThereIsAnEmptySotAndWorkListIsFullThanMoveForwardOneMeterAndAddPositionToWorkListAndClearWorkListThenSaveSpotToResultList(){

    }

    //__________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________


    
}
