package parking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParkingStretchTest {
    @Test
    void fiveMetersParkingSpaceSuitable(){
        ParkingStretch stretch = new ParkingStretch(0,5);
        assertEquals(0, stretch.getStartPosition());
        assertEquals(5, stretch.getLength());
        assertEquals(5, stretch.getEndPosition());
        assertTrue(stretch.isSuitable());

    }
}
