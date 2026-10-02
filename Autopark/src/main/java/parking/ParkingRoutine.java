package parking;

abstract class ParkingRoutine {
   public final void execute(){
   }
   abstract ParkingSpace findSpace();
   void driveToEnd(){}

   void moveToSpace(){}
   
   void park(){}

}
