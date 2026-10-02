package parking;

import java.util.ArrayList;
import java.util.List;

public class ParkingStretch {
    private int startPosition;
    private int length;

    public ParkingStretch(int startPosition, int length){
        this.startPosition = startPosition;
        this.length = length;
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
