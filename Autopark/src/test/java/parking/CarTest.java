package parking;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarTest {

    /*
    Those are the testings that targets the function "MoveForward".
    */

    @Test
    void ifTheCarIsParkedThanDontMoveAndSendSignalCannotMoveWhileParked(){

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
