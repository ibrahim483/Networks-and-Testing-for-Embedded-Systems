package parking;

import java.util.ArrayList;
import java.util.List;

public class FirstSuitableRoutine extends ParkingRoutine{
    private List<ParkingStretch> spots;

    public FirstSuitableRoutine(List<ParkingStretch> spots){
        this.spots = spots;
    }
    @Override
    ParkingStretch findSpace(){
        for(ParkingStretch spot : spots){
            if(spot.isSuitable()){
                return spot;
            }
        }
        return null;
    }
}
