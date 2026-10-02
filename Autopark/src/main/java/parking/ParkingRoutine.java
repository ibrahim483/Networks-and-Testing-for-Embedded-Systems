package parking;

import java.util.List;

abstract class ParkingRoutine {
 private Car car;

 public ParkingRoutine(Car car){this.car = car; }

   public final boolean execute(){
    State state = car.getState();
    if (state.getParkStatus() == CarStatus.PARKED) {
     return true;
    }
    while (state.getPosition() < Car.STREET_LENGTH) {
     int previousPosition = state.getPosition();
     car.moveForward();
     if (state.getPosition() == previousPosition) {
      return false;
     }
    }
    ParkingStretch space = findSpace(state.getParkingStretches());
    if (space == null) {
     state.setParkingStatus(CarStatus.NOPARKING);
     return false;
    }
    while (state.getPosition() > space.getEndPosition()) {
     int previousPosition = state.getPosition();
     car.moveBackward();
     if (state.getPosition() == previousPosition) {
      return false;
     }
    }
    car.park();
    return state.getParkStatus() == CarStatus.PARKED;
    }
   public abstract ParkingStretch findSpace(List<ParkingStretch> spaces);



}
