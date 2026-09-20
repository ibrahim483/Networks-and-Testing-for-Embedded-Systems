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

    /**
     * Description: If the car is already at the end of a free 5 m stretch it parks
     *              immediately; otherwise it drives forward, one meter at a time,
     *              until 5 consecutive free positions have been detected, then parks.
     *              If the end of the street is reached without a stretch, the status
     *              becomes NOPARKING.
     * Pre-condition:  none (a car that is already PARKED is left unchanged).
     * Post-condition: This method guarantees that the car will end up either parked at a 
     *                 position i that is the end of a parking stretch, or that the car 
     *                 successfully understands that there is no available parking.
     * 
     */
    public void park(){

        int carPosition = cState.getPosition();
        CarStatus status = cState.getParkStatus();
        int counter = cState.validParkingSpace(carPosition);
        ArrayList<ParkingSpace> parkings= cState.getDetectedSpace();        


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
                if (cState.getPosition() == 500 && parkingFond == false)
                {
                    cState.setParkingStatus(CarStatus.NOPARKING);
                    return ;
                }
                else if (parkingFond) 
                {
                    System.out.println("parking maneuver..");
                    cState.setParkingStatus(CarStatus.PARKED);
                    return ;
                }       
                else
                {
                    carPosition = cState.getPosition();
                    moveForward();
                    counter = cState.isCurrentTaken(carPosition) ? 0 : counter + 1;
                    parkingFond = counter == 5;
                }
                 
            }

            return ;
        }
    }

    /**
     * Description: Releases a parked car so it can drive again.
     * Pre-condition:  none (if the car is not PARKED nothing changes).
     * Post-condition: status == UNPARKED if it was PARKED, else unchanged.
     */
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
    */
    public State moveForward(){

        if(cState.getPosition() >= lengthOfStreet){
            System.out.println("Limit Reached can't move forward");  
            return cState;
        } else {
            
            int distance = isEmpty();
            if (distance >= 100 && distance <= 200) {
                ParkingSpace parking1 =  new ParkingSpace(cState.getPosition(), false);
                cState.addDetectedSpace(parking1);
                
            }else{
                ParkingSpace parking2 =  new ParkingSpace(cState.getPosition(), true);
                cState.addDetectedSpace(parking2);
            }
            cState.setPosition(cState.getPosition() + 1);
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


    /** 
     * @return State
     */
    public  State getState(){
        return cState;
    }
    /** 
     * @return int
     */
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
    /** 
     * @return State
     */
    public State whereIs(){
        return this.cState;
    }
}

