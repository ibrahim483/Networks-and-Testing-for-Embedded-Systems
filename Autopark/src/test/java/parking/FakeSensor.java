package parking;

import java.util.Random;

public class FakeSensor implements Sensor {
    
    private int[] readings = new int[5]; 
// FakeSensor.java, rad 10-12
    public FakeSensor(int... values) {
        for(int v = 0 ; v < values.length; v++) {   // readings.length → values.length
            readings[v] = values[v];
    }
}



    public int[] getDistance() {
        return readings;
    }

    public int[] emptySpace()
    {
        Random rand = new Random();
        for (int i = 0; i < readings.length; i++) {
            readings[0] = rand.nextInt(100) + 100;
        }
        return readings;
    } 

    public int[] takenSpace()
    {
        Random rand = new Random();
        for (int i = 0; i < readings.length; i++) {
            readings[0] = rand.nextInt(99) ;
        }
        return readings;
    } 

    
}