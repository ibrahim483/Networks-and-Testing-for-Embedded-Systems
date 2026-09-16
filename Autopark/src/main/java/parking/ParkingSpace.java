package parking;

public class ParkingSpace {
      
    
    private final int position;
    private final boolean taken;
    
    
        public ParkingSpace(int position, boolean status) {
        this.position = position;
        this.taken = status;
    }


    public boolean isTaken()
    {
        return taken;
    }

    @Override 
    public boolean equals(Object o){
        ParkingSpace p = (ParkingSpace) o ;

        return p.taken == this.taken && p.position == this.position;
    }
}
