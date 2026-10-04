package parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ParkTest {


    Actuator actuator;
    Sensor sensorA;
    Sensor sensorB;
    ArrayList<ParkingSpace> pSpaces;
    int simulationPosition;
    State simulationState;
    Car c;

    StreetFileReader reader;
    ArrayList<Integer> streetList;

    @BeforeEach 
    public void setup(){

        actuator = mock(Actuator.class);
        sensorA = mock(Sensor.class);
        sensorB = mock(Sensor.class);
        simulationPosition = 0;
        pSpaces = new ArrayList<>();
        simulationState = new State(simulationPosition, CarStatus.UNPARKED, pSpaces);
        simulationState = new State(simulationPosition, CarStatus.UNPARKED, pSpaces);
        c = new Car(sensorA, sensorB, simulationState, actuator);
        reader = new StreetFileReader();
        streetList = reader.readStreet("C:\\Users\\khaawa22\\Documents\\GitHub\\Networks-and-Testing-for-Embedded-Systems\\Street.txt");

        simulationPosition = c.getState().getPosition();
        when(actuator.getPosition()).thenAnswer(invocation -> simulationPosition);
        when(actuator.moveForward()).thenAnswer(invocation -> {
            if (simulationPosition >= 500) {
                return false;
            }
        
            simulationPosition++;
            return true;
        });

        when(actuator.moveBackward()).thenAnswer(invocation -> {
            if (simulationPosition <= 0) {
                return  false;
            }
            simulationPosition--;
            return true;
        });
        when(sensorA.getDistance()).thenAnswer(invocation -> {
            int p = streetList.get(simulationPosition);
            return new int [] {p,p,p,p,p};
        });
        when(sensorB.getDistance()).thenAnswer(invocation -> {
            int p = streetList.get(simulationPosition);
            return new int [] {p,p,p,p,p};
        });
    }


    @Test
    public void AlreadyParkedTest (){

        
        simulationState = new State(10, CarStatus.PARKED, new ArrayList<ParkingSpace>() );
        c = new Car(sensorA, sensorB, simulationState);

        c.park(new SmallestSuitableRoutine());

        assertEquals(simulationState.getParkStatus(), CarStatus.PARKED);
        
    }

    @Test 
    public void endOfStreetNoParkingFound () {

        ArrayList<ParkingSpace> l = new ArrayList<ParkingSpace>();
        for (int i = 0; i <= 500; i++) {
            ParkingSpace space = new ParkingSpace(i, true);
            l.add(space);
        }
         simulationState = new State(500, CarStatus.UNPARKED, l );
         c = new Car(sensorA, sensorB, simulationState);

        c.park(new SmallestSuitableRoutine());
        simulationState = c.getState();
        assertEquals(c.getState().getParkStatus(), CarStatus.NOPARKING);
        
    }


    //Old test for when the car parks as soon as an available slot is available, removed due to our new requirement where the car 
    //has to drive to the end then find a suitible parking place based on routine
    // @Test 
    // public void parkWhenAParkingIsAvailable(){
    //     ArrayList<ParkingSpace> l = new ArrayList<ParkingSpace>();
    //     FakeSensor a = new FakeSensor(true);
    //     FakeSensor b = new FakeSensor(true);

    //     State s = new State(0, CarStatus.UNPARKED, l );
    //     Car   c = new Car(a, b, s);

    //     c.park();

    //     assertEquals(c.getState().getParkStatus(), CarStatus.PARKED);
    // }

    @Test 
    public void scanTheStreetAndParkUsingSmallestSuitableRoutine(){

        c.park(new SmallestSuitableRoutine());
        assertEquals(c.getState().getParkStatus(), CarStatus.PARKED);
        assertEquals(c.getState().getPosition(), 364);//363 is the place where in the street file where the first/smallest
        for (int i = c.getState().getPosition(); i <= c.getState().getPosition() + 5; i++) {
            assertEquals(c.getState().getDetectedSpace().get(i).isTaken(), true);
        }

    }


    @Test 
    public void scanTheStreetAndParkUsingFirstSuitableRoutine(){
        c.park(new FirstSuitableRoutine());
        assertEquals(c.getState().getParkStatus(), CarStatus.PARKED);
        assertEquals(c.getState().getPosition(), 468);
         for (int i = c.getState().getPosition(); i <= c.getState().getPosition() + 5; i++) {
            assertEquals(c.getState().getDetectedSpace().get(i).isTaken(), true);
        }
    }
 

    // @Test 
    // public void parkOnAvailableParkingSpaceOnEntry()
    // {
    //     ArrayList<ParkingSpace> l = new ArrayList<ParkingSpace>();
    //     ParkingSpace space;
    //     for (int i = 0; i <= 500; i++) {
    //         if (i <= 5 && i >= 0) {
    //              space = new ParkingSpace(i, false);
    //         }
    //         else
    //         {
    //              space = new ParkingSpace(i, true);
    //         }
    //         l.add(space);
    //     }

    //     State s = new State(5, CarStatus.UNPARKED, l );
    //     Car   c = new Car(null, null, s);

    //     c.park();

    //     assertEquals(c.getState().getParkStatus(), CarStatus.PARKED);
    // }


}