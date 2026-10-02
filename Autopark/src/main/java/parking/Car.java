package parking;

import java.util.ArrayList;

public class Car {
    public static final int STREET_LENGTH = 500;
    private final State cState;
    private final Sensor sensorA;
    private final Sensor sensorB;
    private final Actuator actuator;
    private boolean sensorADisabled;
    private boolean sensorBDisabled;

    /** Phase 1 constructor: keep the supplied state and start the actuator there. */
    public Car(Sensor sensorA, Sensor sensorB, State cState) {
        this.sensorA = sensorA;
        this.sensorB = sensorB;
        this.cState = cState;
        this.actuator = new SimulatedActuator(cState.getPosition());
    }

    public Car(Sensor sensorA, Sensor sensorB) {
        this(sensorA, sensorB, new State(0, CarStatus.UNPARKED, new ArrayList<>()));
    }

    /**
     * Description: Inject the Phase 2 hardware interfaces.
     * Pre: actuator is non-null, in [0,500]; sensors describe the same street.
     * Post: an unparked car starts at the actuator's position, with no observations.
     * Tests: Phase2IntegrationTest, scenario4BoundariesAndRefusedMovement.
     */
    public Car(Sensor sensorA, Sensor sensorB, Actuator actuator) {
        this.sensorA = sensorA;
        this.sensorB = sensorB;
        this.actuator = actuator;
        this.cState = new State(actuator.getPosition(), CarStatus.UNPARKED, new ArrayList<>());
    }

    /**
     * Description: Park at five known free cells immediately behind the car,
     * or search forward until five free cells are found.
     * Pre: State and actuator positions agree; cells represent one meter each.
     * Post: PARKED at the end of five free cells, or NOPARKING at the street end.
     * If movement is refused, stop with the current position/status unchanged.
     * The maneuver is an abstract status change, as in Phase 1.
     * Tests: ParkTest; parkingIsAllowedWhenTheFiveFreeCellsEndAtPosition500.
     */
    public void park() {
        if (cState.getParkStatus() == CarStatus.PARKED) {
            return;
        }
        // Recompute from observations; no stale parkingFond flag between calls.
        while (cState.validParkingSpace(cState.getPosition()) < 5
                && cState.getPosition() < STREET_LENGTH) {
            int previousPosition = cState.getPosition();
            moveForward();
            if (cState.getPosition() == previousPosition) {
                return; // A refused command must not cause an endless search.
            }
        }
        if (cState.validParkingSpace(cState.getPosition()) == 5) {
            cState.setParkingStatus(CarStatus.PARKED);
        } else {
            cState.setParkingStatus(CarStatus.NOPARKING);
        }
    }

    /**
     * Description: Complete the abstract un-parking maneuver.
     * Pre: none. Post: PARKED becomes UNPARKED at the same street coordinate;
     * other statuses stay unchanged. Tests: UnParkTest, scenarios 1-4.
     */
    public void unPark() {
        if (cState.getParkStatus() == CarStatus.PARKED) {
            cState.setParkingStatus(CarStatus.UNPARKED);
        }
    }

    /**
     * Description: Ask the actuator to move one meter forward, then scan.
     * Pre: actuator and State agree. Post: accepted movement updates position
     * and one observation; at 500, while parked, or if refused, nothing changes.
     * Tests: moveForwardTest, scenarios 1-4.
     */
    public State moveForward() {
        if (cState.getParkStatus() != CarStatus.PARKED
                && cState.getPosition() < STREET_LENGTH && actuator.moveForward()) {
            recordPosition();
        }
        return cState;
    }

    /**
     * Description: Ask the actuator to move one meter backward, then scan.
     * Pre: actuator and State agree. Post: same as moveForward, with lower bound 0.
     * Tests: moveBackwardTest, scenario2FirstSuitableSpaceAndFailurePersistsBelowMidpoint.
     */
    public State moveBackward() {
        if (cState.getParkStatus() != CarStatus.PARKED
                && cState.getPosition() > 0 && actuator.moveBackward()) {
            recordPosition();
        }
        return cState;
    }

    private void recordPosition() {
        cState.setPosition(actuator.getPosition());
        int distance = isEmpty();
        // At boundary p, the sensor views the cell [p-1,p); at 0 it views cell 0.
        int cell = Math.max(0, cState.getPosition() - 1);
        cState.addDetectedSpace(new ParkingSpace(cell, distance < 100));
    }

    /** Phase 1 alias for whereIs(). */
    public State getState() {
        return cState;
    }

    /**
     * Description: Read at least five samples per active sensor. Ignore samples
     * outside 0-200 cm and average the remaining values per sensor, then combine.
     * An entirely invalid batch disables that sensor for the rest of the run.
     * Pre: each active sensor supplies a batch of at least five readings.
     * Post: distance 0-200; return 0 (blocked) when neither sensor is usable.
     * Malformed batches are also treated as failed; position/status do not change.
     * Tests: IsEmptyTest; carFiltersOutOfBoundsSamplesBeforeAveraging;
     * carReadsAFailedSensorOnlyOnceButKeepsReadingTheHealthySensor; scenarios 1-3.
     */
    public int isEmpty() {
        int averageA = sensorADisabled ? -1 : average(sensorA);
        int averageB = sensorBDisabled ? -1 : average(sensorB);
        sensorADisabled = averageA == -1;
        sensorBDisabled = averageB == -1;
        if (averageA >= 0 && averageB >= 0) {
            return (averageA + averageB) / 2;
        }
        if (averageA >= 0) {
            return averageA;
        }
        if (averageB >= 0) {
            return averageB;
        }
        return 0;
    }

    private int average(Sensor sensor) {
        if (sensor == null) {
            return -1;
        }
        int[] readings = sensor.getDistance();
        if (readings == null || readings.length < 5) {
            return -1;
        }
        int sum = 0;
        int count = 0;
        for (int reading : readings) {
            if (reading >= 0 && reading <= 200) {
                sum += reading;
                count++;
            }
        }
        return count == 0 ? -1 : sum / count;
    }

    /** Return current position/status/observations without changing them. Tests: WhereIsTest. */
    public State whereIs() {
        return cState;
    }
}
