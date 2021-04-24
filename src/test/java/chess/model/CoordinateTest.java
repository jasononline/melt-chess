package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {

    @Test
    public void indexToCoordinate() {
        assertArrayEquals(Coordinate.fromIndex(0), new int[]{0, 0});
        assertArrayEquals(Coordinate.fromIndex(63), new int[]{7, 7});
        assertArrayEquals(Coordinate.fromIndex(33), new int[]{1, 4});
    }

    @Test
    public void isLeftMost() {
        assertTrue(Coordinate.isLeftMost(0));
        assertTrue(Coordinate.isLeftMost(32));
        assertFalse(Coordinate.isLeftMost(1));
        assertFalse(Coordinate.isLeftMost(23));
    }

    @Test
    public void isRightMost() {
        assertFalse(Coordinate.isRightMost(0));
        assertFalse(Coordinate.isRightMost(1));
        assertTrue(Coordinate.isRightMost(7));
        assertTrue(Coordinate.isRightMost(23));
    }

    @Test
    public void isUpMost() {
        assertTrue(Coordinate.isUpMost(0));
        assertTrue(Coordinate.isUpMost(1));
        assertTrue(Coordinate.isUpMost(7));
        assertFalse(Coordinate.isUpMost(23));
    }

    @Test
    public void isDownMost() {
        assertFalse(Coordinate.isDownMost(0));
        assertFalse(Coordinate.isDownMost(23));
        assertTrue(Coordinate.isDownMost(57));
        assertTrue(Coordinate.isDownMost(56));
    }
}