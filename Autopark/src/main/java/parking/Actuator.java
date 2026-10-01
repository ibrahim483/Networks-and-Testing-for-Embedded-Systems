package parking;

public interface Actuator {
    boolean moveForward();
    boolean moveBackward();
    int getPosition();
}
