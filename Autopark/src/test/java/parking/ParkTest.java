package parking;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

class ParkTest {




    @Test
    public void AlreadyParkedTest (){

        State s = new State(10, CarStatus.PARKED, new ArrayList<ParkingSpace>() );
        State sCopy = new State(s.getPosition(), s.getParkStatus(), s.getDetectedSpace());
        Car   c = new Car(null, null, s);

        c.park();

        assertEquals(s, sCopy);
        
    }

    @Test 
    public void endOfStreetNoParkingFound () {

        List l = new ArrayList<ParkingSpace>();
        for (int i = 0; i <= 500; i++) {
            ParkingSpace space = new ParkingSpace(i, false);
            l.add(space);
        }
        State s = new State(500, CarStatus.UNPARKED, l );
        Car   c = new Car(null, null, s);

        c.park();
        s = c.getState();
        assertEquals(c.getState().getParkStatus(), CarStatus.NOPARKING);
        
    }

    @Test 
    public void parkWhenAParkingIsAvailable(){
        List l = new ArrayList<ParkingSpace>();
        ParkingSpace space;
        for (int i = 0; i <= 500; i++) {
            if (i <= 100 && i >= 95) {
                 space = new ParkingSpace(i, true);
            }
            else
            {
                 space = new ParkingSpace(i, false);
            }
            l.add(space);
        }
        State s = new State(0, CarStatus.UNPARKED, l );
        Car   c = new Car(null, null, s);

        c.park();

        assertEquals(c.getState().getParkStatus(), CarStatus.PARKED);
        System.out.println(c.getState().getPosition() + " " + c.getState().getDetectedSpace() + " " + c.getState().getParkStatus());
    }

    @Test void NoParkingYetMoveTest(){
        


    }

}