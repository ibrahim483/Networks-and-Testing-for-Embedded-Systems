package parking;

import java.util.Random;

public class FakeSensor implements Sensor {
    
    private int[] readings; 

    public FakeSensor(int... values) {
        readings = new int[5];
        for(int v = 0 ; v < readings.length; v++) {
            readings[v] = values[v];
        }
    } 
    
    public FakeSensor(){
        readings = new int[5];
    }



    public int[] getDistance() {
        return readings;
    }

    public int[] emptySpace()
    {
        Random rand = new Random();
        for (int i = 0; i < readings.length; i++) {
            readings[i] = rand.nextInt(100) + 100;
        }
        return readings;
    } 

    public int[] takenSpace()
    {
        Random rand = new Random();
        for (int i = 0; i < readings.length; i++) {
            readings[i] = rand.nextInt(99) ;
        }
        return readings;
    } 

    
}