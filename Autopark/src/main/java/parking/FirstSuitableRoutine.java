package parking;

import java.util.List;

public class FirstSuitableRoutine extends ParkingRoutine {
    public FirstSuitableRoutine(Car car) {
        super(car);
    }

    /** Return the first stretch with length >= 5, or null. Input is in street order. */
    @Override
    public ParkingStretch findSpace(List<ParkingStretch> spaces) {
        for (ParkingStretch space : spaces) {
            if (space.isSuitable()) {
                return space;
            }
        }
        return null;
    }
}
