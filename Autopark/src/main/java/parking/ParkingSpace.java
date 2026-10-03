package parking;

public class ParkingSpace {
      
    
    private final int position;
    private final boolean taken;
    
    
        public ParkingSpace(int position, boolean taken) {
        this.position = position;
        this.taken = taken;
    }


    public boolean isTaken()
    {
        return taken;
    }

    public int getPosition()
    {
        return position;
    }

    @Override 
    public boolean equals(Object o){

        ParkingSpace p = (ParkingSpace) o ;

        return p.taken == this.taken && p.position == this.position;
    }

}
