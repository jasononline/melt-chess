package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Contains methods to test the methods of the Coordinate class
 */
public class CoordinateTest {

	/**
	 * Method to test the expected behavior of the Method Coordinate.fromIndex()
	 */
	@Test
	public void indexToCoordinate() {
		assertArrayEquals(Coordinate.fromIndex(0), new int[] { 0, 0 });
		assertArrayEquals(Coordinate.fromIndex(63), new int[] { 7, 7 });
		assertArrayEquals(Coordinate.fromIndex(33), new int[] { 1, 4 });
	}

	/**
	 * Method to test the expected behavior of the Method Coordinate.toString()
	 */
	@Test
	public void indexToString() {
		assertEquals(Coordinate.toString(0), "a8");
		assertEquals(Coordinate.toString(63), "h1");
		assertEquals(Coordinate.toString(33), "b4");
	}

	/**
	 * Method to test the expected behavior of the Method Coordinate.toIndex()
	 */
	@Test
	public void coordinateToIndex() {
		assertEquals(Coordinate.toIndex("a8"), 0);
		assertEquals(Coordinate.toIndex("h1"), 63);
		assertEquals(Coordinate.toIndex("b4"), 33);

	}

	/**
	 * Method to test the expected behavior of the Method Coordinate.isLeftMost()
	 */
	@Test
	public void isLeftMost() {
		assertTrue(Coordinate.isOnLeftBorder(0));
		assertTrue(Coordinate.isOnLeftBorder(32));
		assertFalse(Coordinate.isOnLeftBorder(1));
		assertFalse(Coordinate.isOnLeftBorder(23));
	}

	/**
	 * Method to test the expected behavior of the Method Coordinate.isRightMost()
	 */
	@Test
	public void isRightMost() {
		assertFalse(Coordinate.isOnRightBorder(0));
		assertFalse(Coordinate.isOnRightBorder(1));
		assertTrue(Coordinate.isOnRightBorder(7));
		assertTrue(Coordinate.isOnRightBorder(23));
	}

	/**
	 * Method to test the expected behavior of the Method Coordinate.inUpMost()
	 */
	@Test
	public void isUpMost() {
		assertTrue(Coordinate.isOnUpperBorder(0));
		assertTrue(Coordinate.isOnUpperBorder(1));
		assertTrue(Coordinate.isOnUpperBorder(7));
		assertFalse(Coordinate.isOnUpperBorder(23));
	}

	/**
	 * Method to test the expected behavior of the Method Coordinate.isDownMost()
	 */
	@Test
	public void isDownMost() {
		assertFalse(Coordinate.isOnLowerBorder(0));
		assertFalse(Coordinate.isOnLowerBorder(23));
		assertTrue(Coordinate.isOnLowerBorder(57));
		assertTrue(Coordinate.isOnLowerBorder(56));
	}
}