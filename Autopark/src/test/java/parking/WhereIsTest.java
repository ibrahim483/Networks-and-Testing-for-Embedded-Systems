package parking;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
public class WhereIsTest {
    @Test
    public void whereIs_carUnparked_returnPositionandstatusUnparked(){

        //Unparked at 250
        State initialState = new State(250,CarStatus.UNPARKED, new ArrayList<>());
        Car car = new Car(null, null, initialState);
        State resultState = car.whereIs();
        assertEquals(250, resultState.getPosition());
        assertEquals(CarStatus.UNPARKED, resultState.getParkStatus());

    }

    @Test
    public void whereIs_carParked_returnPositionandstatusParked(){
        State initialState = new State(100,CarStatus.PARKED, new ArrayList<>());
        Car car = new Car(null, null, initialState);
        State resultState = car.whereIs();
        assertEquals(100, resultState.getPosition());
        assertEquals(CarStatus.PARKED, resultState.getParkStatus());
    }
    @Test
    public void whereIs_noparking_returnpositionandstatusNoparking(){
        State initialState = new State(500,CarStatus.NOPARKING, new ArrayList<>());
        Car car = new Car(null, null, initialState);
        State resultState = car.whereIs();
        assertEquals(500, resultState.getPosition());
        assertEquals(CarStatus.NOPARKING, resultState.getParkStatus());
    }  
}
