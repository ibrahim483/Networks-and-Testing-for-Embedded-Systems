package parking;


import java.util.ArrayList;

public class Car {
    private State cState ;
    private boolean parkingFond = false;
    private final int lengthOfStreet = 500;
    private  Sensor sensorA;
    private  Sensor sensorB;

    public Car(Sensor sensorA, Sensor sensorB, State cState) {
        this.sensorA = sensorA;
        this.sensorB = sensorB;
        this.cState  = cState;
    }

    public Car(Sensor sensorA, Sensor sensorB) {
        this(sensorA, sensorB, new State(0, CarStatus.UNPARKED, new ArrayList<>()));
    }


    public void park(){

        int carPosition = cState.getPosition();
        CarStatus status = cState.getParkStatus();
        int counter = cState.validParkingSpace(carPosition);
        ArrayList<ParkingSpace> parkings= cState.getDetectedSpace();        
        State newState = new State(carPosition, status, parkings);


        if (status == CarStatus.PARKED){
            System.out.println("Car is Already Parked...");
        }
        else if (counter == 5) {
            System.out.println("Parking maneuver...");
            cState.setDetectedSpace(parkings);
            cState.setParkingStatus(CarStatus.PARKED);
            cState.setPosition(carPosition);
        }
        else
        {
            for (int i = 0; i <= 500 - carPosition; i++) {
                if (carPosition == 500 && parkingFond == false)
                    {
                        cState.setParkingStatus(CarStatus.NOPARKING);
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

    /**
     Description: Moves the car forward by one meter. After moving, the sensors are read (isEmpty())
                  and the new position is recorded in the detected spaces list: as taken (true) unless
                  the measured distance is between 100 and 200, in which case it is recorded as free (false).
     Pre-condition: The car position is not beyond the end of the street (position <= lengthOfStreet).
     Post-condition: If the limit was reached, the state is unchanged and "Limit Reached can't move forward"
                     is printed. Otherwise the position is increased by 1 and exactly one ParkingSpace
                     for the new position is added to the detected spaces. The current State is returned.
     Test-cases: moveForwardTest
                 - the position increases by 1 after one call
                 - the detected space of the next position is recorded as not taken
                 - the position is recorded correctly after 500 moves (end of the street)
                 - at the end of the street the car does not move and "Limit Reached" is signalled
    */
    public State moveForward(){

        if(cState.getPosition() > lengthOfStreet){
            System.out.println("Limit Reached can't move forward");  
            return cState;
        } else {
            cState.setPosition(cState.getPosition() + 1);

            int distance = isEmpty();
            if (distance >= 100 && distance <= 200) {
                ParkingSpace parking1 =  new ParkingSpace(cState.getPosition(), false);
                cState.addDetectedSpace(parking1);

            }else{
                ParkingSpace parking2 =  new ParkingSpace(cState.getPosition(), true);
                cState.addDetectedSpace(parking2);
            }
        }
        return cState;
        }

    /**
     Description: Moves the car backward by one meter. No sensor reading or space detection is done.
     Pre-condition: The car position is greater than 0 (not at the beginning of the street).
     Post-condition: If the position is 0, the state is unchanged and a message is printed that the car
                     can't move backward. Otherwise the position is decreased by 1. The current State
                     is returned.
     Test-cases: moveBackwardTest
                 - at the beginning of the street (position 0) the car stays at position 0
    */
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
        int[] valA = sensorA.getDistance();
        int[] valB = sensorB.getDistance();
        
        for (int i = 0 ; i < valA.length ; i++) {
            sumB += valB[i];
            sumA += valA[i]; 
        }

        sumB = sumB/valB.length;
        sumA = sumA/valA.length;

        int tempA = valA[0];
        int tempB = valB[0];
        for (int i = 0 ; i < valA.length ; i++) {
            if (tempA - sumA >= valA[i] - sumA && tempA - sumA != 0) {
                tempA = valA[i];
                System.out.println(tempA);
            }
            if (tempB - sumB >= valB[i] - sumB && tempB - sumB != 0) {
                tempB = valB[i];
            }
        }

        if (tempA < 0 || tempA > 200) {
            aReliable = false;
        }
        if (tempB < 0 || tempB > 200) {
            bReliable = false;
        }


        if(aReliable && bReliable) {
        return (tempA + tempB )/ 2;
    } else if (aReliable) {
        return tempA;
    } else if (bReliable) {
        return tempB;
    } else {
        return 0; // Both sensors are unreliable
    }
    }
    public State whereIs(){
        return this.cState;
    }
}

