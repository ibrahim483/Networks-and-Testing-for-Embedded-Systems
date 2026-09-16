package parking;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class UnParkTest {
    
    @Test 
    public void ifTheCarIsParkedThenUnPark(){
        
        State s = new State(100, CarStatus.PARKED, new ArrayList<>());
        Car   c = new Car(null, null, s);
        c.unPark();

        assertEquals(c.getState().getParkStatus(), CarStatus.UNPARKED);
    }


    @Test 
    public void ifTheCarIsNotParkedThenUnPark(){
        State s = new State(100, CarStatus.UNPARKED, new ArrayList<>());
        Car   c = new Car(null, null, s);
        c.unPark();

        assertEquals(c.getState().getParkStatus(), CarStatus.UNPARKED);
    }
}
