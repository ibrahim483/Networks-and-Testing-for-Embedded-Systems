package parking;

public interface Actuator {
    int position = 0;
    boolean moveForward();
    boolean moveBackward();
    int getPosition();
}
