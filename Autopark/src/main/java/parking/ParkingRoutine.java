package parking;

import java.util.ArrayList;

abstract class ParkingRoutine {
   
   abstract ParkingStretch findSpace(ArrayList<ParkingSpace> pSpace);
}
