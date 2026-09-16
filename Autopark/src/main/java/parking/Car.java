package parking;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private int position = 0;
    private CarStatus status = CarStatus.UNPARKED;
    private final List<ParkingSpace> knownSpaces = new ArrayList<>();
    private final Sensor sensorA;
    private final Sensor sensorB;

    public Car(Sensor sensorA, Sensor sensorB) {
        this.sensorA = sensorA;
        this.sensorB = sensorB;

    }

    // TC1: both sensors reliable -> average 5 readings from each
    public int isEmpty() {
        System.out.println("isEmpty place");
      return 0;
        }


        /**
         * Queries both ultrasound sensors at least 5 times each, filters out
         * noise, and returns the distance in cm to the nearest object on the
         * right-hand side. A sensor whose readings are too noisy to be
         * reliable is disregarded entirely.
         * Pre-condition: none (sensors may return values in 0-200, or noisy/erratic values)
         * Post-condition: return value is a distance in cm (0-200)
         * Test-cases:
         *   TC1: both sensors clean -> return average of both
         *   TC2: sensor A noisy, sensor B clean -> return sensor B's average only
         *   TC3: sensor B noisy, sensor A clean -> return sensor A's average only
         *   TC4: both sensors noisy -> throw SensorFailureException
         *   TC5: a reading falls outside 0-200 -> treated as an invalid/noisy sample
         */

    public State moveForward() {
        return null;
        /**
         * Moves the car 1 meter forward along the street, queries both sensors
         * via isEmpty(), updates the detected parking spaces, and returns the
         * new state of the car.
         * Pre-condition: position < 500 (car is not already at the end of the street)
         * Post-condition: position == old position + 1; detectedSpaces updated
         *                  if a new 5m stretch has just been confirmed
         * Test-cases:
         *   TC1: position=0, sensors report clear -> position=1
         *   TC2: position=499 -> position=500
         *   TC3: position=500 (already at end) -> IllegalStateException, position unchanged
         *   TC4: 5 consecutive clear readings ending at position=5 -> new ParkingSpace(0,5) added
         */
    }
    public State moveBackwards() {
        return null;
        /**
         * Moves the car 1 meter backward along the street, queries both sensors
         * via isEmpty(), and returns the new state of the car.
         * Pre-condition: position > 0 (car is not already at the beginning of the street)
         * Post-condition: position == old position - 1
         * Test-cases:
         *   TC1: position=1 -> position=0
         *   TC2: position=0 (already at start) -> IllegalStateException, position unchanged
         */
    }
    public State moveSideways(){return null;}
    public State WhereIs(){
        return new State(position, status, knownSpaces);
        /**
         * Returns the current position of the car on the street and its
         * parked/unparked status.
         * Pre-condition: none
         * Post-condition: returned CarState reflects the car's current position,
         *                  status, and all parking spaces detected so far
         * Test-cases:
         *   TC1: freshly constructed car -> position=0, status=UNPARKED
         *   TC2: after moveForward() once -> position=1
         */
    }
    public void Park() {
        /**
         * Performs a pre-programmed reverse parallel parking maneuver. If the
         * car is not currently positioned at a confirmed empty 5m stretch, it
         * first moves the car forward until such a stretch is detected, then
         * parks.
         * Pre-condition: status == UNPARKED
         * Post-condition: status == PARKED, provided a 5m stretch was found
         *                  before reaching the end of the street
         * Test-cases:
         *   TC1: car already at a confirmed 5m space -> status becomes PARKED immediately
         *   TC2: car must drive forward to find a space -> position advances, then PARKED
         *   TC3: no space found before end of street -> throws NoParkingSpaceFoundException
         */
    }
    public void Unpark() {
        /**
         * Moves the car forward (and to the left) out of the parking space,
         * back to the front of the space it was parked in.
         * Pre-condition: status == PARKED
         * Post-condition: status == UNPARKED
         * Test-cases:
         *   TC1: car is parked -> status becomes UNPARKED
         *   TC2: car is not parked -> throws IllegalStateException
         */
    }
}