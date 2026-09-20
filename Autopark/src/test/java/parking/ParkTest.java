package parking;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

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

        ArrayList l = new ArrayList<ParkingSpace>();
        for (int i = 0; i <= 500; i++) {
            ParkingSpace space = new ParkingSpace(i, true);
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
        ArrayList l = new ArrayList<ParkingSpace>();
        ParkingSpace space;
        FakeSensor a = new FakeSensor();
        FakeSensor b = new FakeSensor();

        State s = new State(0, CarStatus.UNPARKED, l );
        Car   c = new Car(a, b, s);

        c.park();

        assertEquals(c.getState().getParkStatus(), CarStatus.UNPARKED);
        System.out.println(c.getState().getPosition() + " " + c.getState().getDetectedSpace() + " " + c.getState().getParkStatus());
    }

    @Test 
    public void parkOnAvailableParkingSpaceOnEntry()
    {
        ArrayList l = new ArrayList<ParkingSpace>();
        ParkingSpace space;
        for (int i = 0; i <= 500; i++) {
            if (i <= 5 && i >= 0) {
                 space = new ParkingSpace(i, false);
            }
            else
            {
                 space = new ParkingSpace(i, true);
            }
            l.add(space);
        }

        State s = new State(5, CarStatus.UNPARKED, l );
        Car   c = new Car(null, null, s);

        c.park();

        assertEquals(c.getState().getParkStatus(), CarStatus.PARKED);
    }

    @Test void NoParkingYetMoveTest(){
        


    }

}