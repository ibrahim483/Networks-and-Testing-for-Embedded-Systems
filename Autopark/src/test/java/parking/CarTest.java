package parking;
import parking.Car;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarTest {


    Sensor a = new FakeSensor(30, 32, 31, 29, 30);
    Sensor b = new FakeSensor(31, 30, 29, 31, 32);

    Car car = new Car(a,b);


    /*
    Those are the testings that targets the function "MoveForward".
    */

    @Test
    void ifTheCarIsParkedThanDontMoveAndSendSignalCannotMoveWhileParked(){
        car.moveForward();



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
