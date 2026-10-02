package parking;

import java.util.List;

public class StreetSensor implements Sensor{
    private final Actuator actuator;
    private final List<ParkingStretch> spaces;
    private final int failurePosition;
    private boolean broken;

    public StreetSensor(Actuator actuator, List<ParkingStretch> spaces, int failurePosition) {
        this.actuator = actuator;
        this.spaces = spaces;
        this.failurePosition = failurePosition;
    }

    @Override
    public int[] getDistance() {
        int position = actuator.getPosition();
        if (failurePosition >= 0 && position >= failurePosition) {
            broken = true;
        }
        if (broken) {
            return new int[] {1000, 1000, 1000, 1000, 1000};
        }

        int cell = Math.max(0, position - 1);
        int distance = 30;
        for (ParkingStretch space : spaces) {
            if (cell >= space.getStartPosition() && cell < space.getEndPosition()) {
                distance = 150;
                break;
            }
        }
        return new int[] {distance - 2, distance - 1, distance, distance + 1, distance + 2};
    }
}
