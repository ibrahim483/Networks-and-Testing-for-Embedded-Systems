package parking;

public class RandomSensor implements Sensor{
    @Override
    public int getDistance() {
        return (int) (Math.random() * 201); 
    }
}