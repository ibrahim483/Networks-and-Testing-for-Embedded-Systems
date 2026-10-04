package parking;

import java.util.ArrayList;

public class FirstSuitableRoutine extends ParkingRoutine{

    @Override
    ParkingStretch findSpace(ArrayList<ParkingSpace> pSpace){
        int count = 0;
        ParkingStretch s = new ParkingStretch(0, 0);
        for (int i = pSpace.size() - 1; i >= 0; i--) {
            if (!pSpace.get(i).isTaken()) {
                count++;
                if (count == 5) {
                    s = new ParkingStretch(i, count);
                    return s;
                }
            }else{count = 0;}
        }
        
        return s;
    }
}
