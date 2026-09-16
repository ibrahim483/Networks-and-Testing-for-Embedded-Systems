package parking;

public class ParkingSpace {
    public final int position;
    public final boolean empty;
public ParkingSpace(int startMeter, boolean endMeter) {
    this.position = startMeter;
    this.empty = endMeter;
}
}
