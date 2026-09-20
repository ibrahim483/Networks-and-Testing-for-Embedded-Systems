package parking;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class ParkTest {




    @Test
    public void AlreadyParkedTest (){

        
        State s = new State(10, CarStatus.PARKED, new ArrayList<ParkingSpace>() );
        Car   c = new Car(null, null, s);

        c.park();

        assertEquals(s.getParkStatus(), CarStatus.PARKED);
        
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

        assertEquals(c.getState().getParkStatus(), CarStatus.PARKED);
    }

    @Test 
    public void shoudlParkAfterFiveSpaces()
    {
        ArrayList l = new ArrayList<ParkingSpace>();
        ParkingSpace space;
        FakeSensor a = new FakeSensor();
        FakeSensor b = new FakeSensor();

        State s = new State(0, CarStatus.UNPARKED, l );
        Car   c = new Car(a, b, s);

        c.park();

        assertEquals(c.getState().getPosition(), 5);
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


}