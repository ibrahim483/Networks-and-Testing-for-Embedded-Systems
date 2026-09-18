package parking;

import java.util.Random;

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