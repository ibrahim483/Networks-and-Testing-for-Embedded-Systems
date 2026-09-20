package parking;

public interface Sensor {
        int[] getDistance();
        int[] emptySpace();
        int[] takenSpace();
    }