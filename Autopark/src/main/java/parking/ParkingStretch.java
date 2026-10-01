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
    public int getParkingSlot(int startPosition, State state){
        int count = 0;
        for(int i = startPosition ; i < 500 && i > state.getDetectedSpace().size(); i++) {
            if (state.isCurrentTaken(i)) {
                break;
            }
            count++;
        }
        return count;
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
