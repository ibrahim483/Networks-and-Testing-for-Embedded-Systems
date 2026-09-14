package parking;

import java.util.LinkedList;
import java.util.Queue;

public class FakeSensor implements Sensor {
    private final Queue<Integer> readings = new LinkedList<>();

    public FakeSensor(int... values) {
        for (int v : values) readings.add(v);
    }


    public int getDistance() {
        return readings.isEmpty() ? 0 : readings.poll();
    }
}