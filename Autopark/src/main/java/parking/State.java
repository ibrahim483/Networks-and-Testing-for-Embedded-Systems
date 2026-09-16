package parking;

import java.util.ArrayList;
import java.util.List;

public class State {
    private  int currentPosition = 0;
    private  CarStatus status = CarStatus.UNPARKED;
    private  List<ParkingSpace> detectedSpaces;


    public State(int currentPosition, CarStatus status, List<ParkingSpace> detectedSpaces) {
        this.currentPosition = currentPosition;
        this.status = status;
        this.detectedSpaces = detectedSpaces;
    }

    public int getPosition(){
        return currentPosition;
    } 
    
    
    public CarStatus getParkStatus(){
        return status;
    }
    
    
    public List<ParkingSpace> getDetectedSpace(){
        return detectedSpaces;
    }


    public void setParkingStatus(CarStatus parkingStatus){
        this.status = parkingStatus;
    }

    public boolean isCurrentTaken(int i)
    {
        return detectedSpaces.get(i).isTaken();
    }
    
    public void setPosition(int position) {
        currentPosition = position;
    }
    
    public void setDetectedSpace(List<ParkingSpace> detectedSpace) {
        this.detectedSpaces = detectedSpace;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State s = (State) o;
        return this.currentPosition == s.currentPosition
            && this.status == s.status
            && this.detectedSpaces.equals(s.detectedSpaces);
    }

}
