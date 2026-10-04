package parking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SensorTest {
    
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
        streetList = reader.readStreet("Street.txt");

        when(actuator.getPosition()).thenAnswer(invocation -> simulationPosition);
        when(actuator.moveForward()).thenAnswer(invocation -> {
            if (simulationPosition >= 500) {
                return false;
            }
        
            simulationPosition++;
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
    public void testFileReaderHasStreetData(){

        
        for (Integer s : streetList) {
            System.out.println(s);
        }
        assertNotNull(streetList);

    }

    @Test 
    public void testSensorReadingsFromIntegerArray(){
       
        ArrayList<Integer> sensorData = new ArrayList<>();
        for (int i = 0; i < streetList.size(); i++) {
            sensorData.add(sensorA.getDistance()[0]);
            c.moveForward();
        }
        for (int i = 0; i < streetList.size(); i++) {
            assertEquals(streetList.get(i), sensorData.get(i));
        }

    }

}
