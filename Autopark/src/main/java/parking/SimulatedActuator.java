package parking;

public class SimulatedActuator implements Actuator{
    private int position;

    public SimulatedActuator() {
        this(0);
    }

public SimulatedActuator(int position) {
    if (position < 0 || position > 500) {
        throw new IllegalArgumentException("Position must be between 0 and 500.");
    }
    this.position = position;
}
    @Override
    public boolean moveForward() {
        if (position == 500) {
            return false;
        }
        position++;
        return true;
    }
    @Override
    public boolean moveBackward() {
        if (position == 0) {
            return false;
        }
        position--;
        return true;
    }
@Override
public int getPosition() {
    return position;
}
}
