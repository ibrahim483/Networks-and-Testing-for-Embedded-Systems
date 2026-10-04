package parking;

import java.util.ArrayList;

public class SmallestSuitableRoutine extends ParkingRoutine{


    ParkingStretch findSpace(ArrayList<ParkingSpace> pSpace){
        
        int count = 0;
        ParkingStretch s = new ParkingStretch(0, 0);
        System.out.println(pSpace.size());
        for (int i = pSpace.size() - 1; i >= 0; i--) {
            if (!pSpace.get(i).isTaken()) {
                count++;
            }else{
                if (count >= 5) {
                    s = new ParkingStretch(i+2, count);// we add 2 to compensate for the -1 we have in the loop and also since here it should be the index if the first taken place
                    if (count == 5) {
                        return s;
                    }
                }
                count = 0;
            
            }
        }
        
        return s;
    }
}
