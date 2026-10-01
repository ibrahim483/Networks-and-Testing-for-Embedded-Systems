package parking;

import java.util.ArrayList;

public class ParkingStretch {
    private int startPosition;
    private int length;
    private ArrayList<Integer> Spots = new ArrayList<>();
    public ParkingStretch(int startPosition, int length){
        this.startPosition = startPosition;
        this.length = length;
    }
    public int getParkingSlot(int startPosition){
        

    }


    public int getStartPosition() {
        return startPosition;
    }

    public int getLength() {
        return length;
    }
    public int getEndPosition(){
        return startPosition + length;
    }
    public boolean isSuitable(){
        return length >= 5;
    }
}
