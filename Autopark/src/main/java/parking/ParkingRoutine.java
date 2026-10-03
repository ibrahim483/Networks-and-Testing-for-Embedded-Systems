package parking;

abstract class ParkingRoutine {
   public final void execute(){
   }
   abstract ParkingStretch findSpace();
   void driveToEnd(){}

   void moveToSpace(){}
   
   void park(){}

}
