package parking;


import java.util.ArrayList;

public class Car {
    private State cState ;
    private boolean parkingFond = false;
    private final Sensor sensorA;
    private final Sensor sensorB;
    private final int lengthOfStreet = 500;

    public Car(Sensor sensorA, Sensor sensorB, State cState) {
        this.sensorA = sensorA;
        this.sensorB = sensorB;
        this.cState  = cState;
    }

    public Car(Sensor sensorA, Sensor sensorB) {
        this(sensorA, sensorB, new State(0, CarStatus.UNPARKED, new ArrayList<>()));
    }


    public void park(){

        int counter = 0;
        State newState = new State(this.cState.getPosition(), this.cState.getParkStatus(), this.cState.getDetectedSpace());
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
                        newState = moveForward();
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


    public void unPark(){
        if (cState.getParkStatus() == CarStatus.PARKED){
            cState.setParkingStatus(CarStatus.UNPARKED);
            System.out.println("Car is UnParked...");
        }
        else
        {
            System.out.println("Car is Not Parked...");
        }
    }


    public State moveForward(){

        if(cState.getPosition() > lengthOfStreet){
            System.out.println("Limit Reached can't move forward");  
            return cState;
        } else {
            cState.setPosition(cState.getPosition() + 1);

            int distance = isEmpty();
            if (distance >= 100 && distance <= 200) {
                ParkingSpace parking1 =  new ParkingSpace(cState.getPosition(), true);
                cState.addDetectedSpace(parking1);

            }else{
                ParkingSpace parking2 =  new ParkingSpace(cState.getPosition(), false);
                cState.addDetectedSpace(parking2);
            }
        }
        return cState;
        }

    public State moveBackward(){

            if(cState.getPosition() == 0){
                System.out.println("The car can't move backward you are at the beginning of the street");  
                return cState;
        } else {
            cState.setPosition(cState.getPosition() - 1);

        }
            return cState;

        }


    public  State getState(){
        return cState;
    }
    public int isEmpty(){
        int sumA = 0;
        int sumB = 0;
        boolean aReliable = true;
        boolean bReliable = true;
        for (int i = 0; i < 5; i++) {
          int valA = sensorA.getDistance();
          int valB = sensorB.getDistance();

          if (valA < 0 || valA > 200) aReliable = false;
          if (valB < 0 || valB > 200) bReliable = false;

          sumA += valA;
          sumB += valB;
        }
        if(aReliable && bReliable) {
        return (sumA + sumB )/ 10;
    } else if (aReliable) {
        return sumA / 5;
    } else if (bReliable) {
        return sumB / 5;
    } else {
        return -1; // Both sensors are unreliable
    }
    }

}