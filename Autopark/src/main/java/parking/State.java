package parking;

import java.util.List;

public class State {
    public final int currentPosition;
    public final CarStatus status;
    public final List<ParkingSpace> detectedSpaces;

    public State(int currentPosition, CarStatus status, List<ParkingSpace> detectedSpaces) {
        this.currentPosition = currentPosition;
        this.status = status;
        this.detectedSpaces = detectedSpaces;
    }
}