package parking;

import java.util.ArrayList;
import java.util.List;

public class State {
    private  int currentPosition = 0;
    private  CarStatus status;
    private  ArrayList<ParkingSpace> detectedSpaces;


    public State(int currentPosition, CarStatus status, ArrayList<ParkingSpace> detectedSpaces) {
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
    
    
    public ArrayList<ParkingSpace> getDetectedSpace(){
        return detectedSpaces;
    }


    public void setParkingStatus(CarStatus parkingStatus){
        this.status = parkingStatus;
    }

    public boolean isCurrentTaken(int i)
    {
        for (ParkingSpace space : detectedSpaces) {
            if(space.getPosition() == i){
                return space.isTaken();
            }
        }
        return true;
    }
    
    public void setPosition(int position) {
        currentPosition = position;
    }
    
    public void setDetectedSpace(ArrayList<ParkingSpace> detectedSpace) {
        this.detectedSpaces = detectedSpace;
    }

    public void addDetectedSpace(ParkingSpace space) {
        for (int i = 0; i < detectedSpaces.size(); i++) {
            if (detectedSpaces.get(i).getPosition() == space.getPosition()) {
                detectedSpaces.set(i, space);
                return;
            }
        }
        detectedSpaces.add(space);
    }

    public int validParkingSpace(int index) {

        int count = 0;
        if (detectedSpaces.isEmpty()) {
            return 0;
        }
        for(int i = index ; i > index - 5 ; i--)
        {
            if (i >= 0 && !isCurrentTaken(i))
            {
                count++;
            }
            else
            {
                return count;
            }
        }
        return count;
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
