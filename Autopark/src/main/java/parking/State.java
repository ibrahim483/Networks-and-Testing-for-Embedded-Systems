package parking;

import java.util.ArrayList;

public class State {
    private  int currentPosition = 0;
    private  CarStatus status = CarStatus.UNPARKED;
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
        return detectedSpaces.get(i).isTaken();
    }
    
    public void setPosition(int position) {
        currentPosition = position;
    }
    
    public void setDetectedSpace(ArrayList<ParkingSpace> detectedSpace) {
        this.detectedSpaces = detectedSpace;
    }

    public void addDetectedSpace(ParkingSpace space) {
        detectedSpaces.add(space);
    }

    public void clearDetectedSpaces() {
        detectedSpaces.clear();
    }

    public void removeDetectedSpace(ParkingSpace space) {
        detectedSpaces.remove(space);
    }

    public void updateDetectedSpace(int index, ParkingSpace space) {
        detectedSpaces.set(index, space);
    }

    public boolean isDetectedSpacesFull() {
        return detectedSpaces.size() >= 500;
    }

    public boolean isDetectedSpacesEmpty() {
        return detectedSpaces.isEmpty();
    }

    public int validParkingSpace(int index) {
        
        int count = 0;
        for(int i = index ; i > index - 5 ; i--)
        {
            if (i >= 0 && !detectedSpaces.get(i).isTaken())
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
