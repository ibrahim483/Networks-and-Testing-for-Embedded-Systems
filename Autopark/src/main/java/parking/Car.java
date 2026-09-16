package parking;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private State cState ;
    private boolean parkingFond;
    private final Sensor sensorA;
    private final Sensor sensorB;

    public Car(Sensor sensorA, Sensor sensorB, State cState) {
        this.sensorA = sensorA;
        this.sensorB = sensorB;
        this.cState  = cState;
    }


    public void park(){

        State tempState = new State(0, CarStatus.UNPARKED, new ArrayList<>());
        int counter = 0;
        if (cState.getParkStatus() == CarStatus.PARKED){
            System.out.println("Car is Already Parked...");
        }
        for (int i = 0; i <= 500; i++) {

            if (tempState.getPosition() == 500 && parkingFond == false)
            {
                tempState.setParkingStatus(CarStatus.NOPARKING);
                parkingFond = false;
            }
            else
            {
                tempState = MoveForward();
                counter = tempState.isCurrentTaken(i) ? counter + 1 : 0;
                parkingFond = counter == 5;
            }
            if (parkingFond) {
                System.out.println("parking maneuver..");
                cState.setParkingStatus(CarStatus.PARKED);
                cState.setPosition(tempState.getPosition());
                cState.setDetectedSpace(tempState.getDetectedSpace());
            }        
        }
    }


    public State MoveForward()
    {

        return  null;
    }
    public  State getState(){
        return cState;
    }
}