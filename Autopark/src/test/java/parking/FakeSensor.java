package parking;

public class FakeSensor implements Sensor {
    
    private int[] readings = new int[5]; 

    public FakeSensor(int... values) {
        for(int v = 0 ; v < readings.length; v++) {
            readings[v] = values[v];
    }
}


    public int[] getDistance() {
        return readings;
    }
}