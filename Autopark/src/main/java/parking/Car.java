package parking;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private State cState ;
    private boolean parkingFond = false;
    private final Sensor sensorA;
    private final Sensor sensorB;

    public Car(Sensor sensorA, Sensor sensorB, State cState) {
        this.sensorA = sensorA;
        this.sensorB = sensorB;
        this.cState  = cState;
    }


    public void park(){

        int counter = 0;
        State newState = new State(counter, null, null);
        if (cState.getParkStatus() == CarStatus.PARKED){
            System.out.println("Car is Already Parked...");
        }
        else
        {
            for (int i = 0; i <= 500; i++) {
                
                if (cState.getPosition() == 500 && parkingFond == false)
                    {
                        cState.setParkingStatus(CarStatus.NOPARKING);
                        parkingFond = false;
                        cState.setPosition(newState.getPosition());
                        cState.setDetectedSpace(newState.getDetectedSpace());
                        return ;
                    }
                else
                    {
                        newState = MoveForward();
                        counter = cState.isCurrentTaken(i) ? 0 : counter + 1;
                        parkingFond = counter == 5;
                    }
                    if (parkingFond) {
                        System.out.println("parking maneuver..");
                        cState.setParkingStatus(CarStatus.PARKED);
                        cState.setPosition(newState.getPosition());
                        cState.setDetectedSpace(newState.getDetectedSpace());
                        return ;
                    }        
                }



                return ;
        }
    }


    public State MoveForward()
    {

        return  new State(100, CarStatus.PARKED, new ArrayList<>());
    }
    public  State getState(){
        return cState;
    }
}