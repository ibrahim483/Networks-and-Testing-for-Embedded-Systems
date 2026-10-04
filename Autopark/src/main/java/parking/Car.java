package parking;


import java.util.ArrayList;

public class Car {
    private State cState ;
    private Actuator actuator;
    private final int lengthOfStreet = 500;
    private  Sensor sensorA;
    private  Sensor sensorB;

    public Car(Sensor sensorA, Sensor sensorB, State cState, Actuator actuator) {
        this.sensorA = sensorA;
        this.sensorB = sensorB;
        this.cState  = cState;
        this.actuator = actuator;
    }

    // Baseline compatibility for existing Phase 1 tests; actuator integration remains Phase 2 work.
    public Car(Sensor sensorA, Sensor sensorB, State cState) {
        this(sensorA, sensorB, cState, null);
    }

    // public Car(Sensor sensorA, Sensor sensorB) {
    //     this(sensorA, sensorB, new State(0, CarStatus.UNPARKED, new ArrayList<>()));
    // }

    
    public void park(ParkingRoutine parking){

        int carPosition = cState.getPosition();
        CarStatus status = cState.getParkStatus();
        ParkingStretch stretch = new ParkingStretch(0, 0);
        if (status == CarStatus.PARKED){
            System.out.println("Car is Already Parked...");
        }
        else
        {
            for (int i = 0; i <= lengthOfStreet - carPosition; i++) {
                if (cState.getPosition() == lengthOfStreet)
                {
                    stretch = parking.findSpace(cState.getDetectedSpace());
                    if(stretch.getLength() < 5){
                        cState.setParkingStatus(CarStatus.NOPARKING);
                        System.out.println("No parking available!!");
                        return;
                    }
                }   
                else
                {
                    moveForward();
                }
            }
            for (int i = 0; i < lengthOfStreet - stretch.getStartPosition(); i++) {
                moveBackward();
            }
            cState.setParkingStatus(CarStatus.PARKED);
            
            ArrayList<ParkingSpace> tempArr = cState.getDetectedSpace();
            for (int i = stretch.getStartPosition(); i < stretch.getStartPosition() + stretch.getLength(); i++) {
                tempArr.set(i, new ParkingSpace(i, true));
            }

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
            ArrayList<ParkingSpace> p = cState.getDetectedSpace();
            for (int i = cState.getPosition(); i <= cState.getPosition() + 5; i++) {
                p.set(i, new ParkingSpace(i, false));
            }
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

            if (actuator != null && !actuator.moveForward()) {
                return cState;
            }
            int distance = isEmpty();

            ParkingSpace newParkingSpace;
            if (distance >= 100 && distance <= 200) {
                newParkingSpace =  new ParkingSpace(cState.getPosition(), false); //false neans the space is not taken
                cState.addDetectedSpace(newParkingSpace);
                
            }else{
                newParkingSpace =  new ParkingSpace(cState.getPosition(), true);
                cState.addDetectedSpace(newParkingSpace);
            }
            setPosition(actuator.getPosition());
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
            if (actuator != null && !actuator.moveBackward()) {
                return cState;
            }
            else {
                setPosition(actuator.getPosition());
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

    public void setState(State state){
        this.cState = state;
    }

    public void setPosition(int position){
        this.cState.setPosition(position);
    }
}

