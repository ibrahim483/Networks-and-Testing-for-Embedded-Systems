package parking;

import java.util.List;

public class SmallestSuitableRoutine extends ParkingRoutine{
    public SmallestSuitableRoutine(Car car){super(car);}
    @Override
    public ParkingStretch findSpace(List<ParkingStretch> spaces){
        ParkingStretch smallest = null;
        for(ParkingStretch space : spaces){
            if(space.isSuitable() && (smallest == null || space.getLength() < smallest.getLength() )){
                smallest = space;
            }
        }
        return smallest;
    }
}
