package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
	public void CoordinateToIndex() {
		assertEquals(Coordinate.toIntex("a8"), 0);
		assertEquals(Coordinate.toIntex("h1"), 63);
		assertEquals(Coordinate.toIntex("b4"), 33);
	}

	/**
	 * Method to test the expected behavior of the Method Coordinate.isLeftMost()
	 */
	@Test
	public void isLeftMost() {
		assertTrue(Coordinate.isLeftMost(0));
		assertTrue(Coordinate.isLeftMost(32));
		assertFalse(Coordinate.isLeftMost(1));
		assertFalse(Coordinate.isLeftMost(23));
	}

	/**
	 * Method to test the expected behavior of the Method Coordinate.isRightMost()
	 */
	@Test
	public void isRightMost() {
		assertFalse(Coordinate.isRightMost(0));
		assertFalse(Coordinate.isRightMost(1));
		assertTrue(Coordinate.isRightMost(7));
		assertTrue(Coordinate.isRightMost(23));
	}

	/**
	 * Method to test the expected behavior of the Method Coordinate.inUpMost()
	 */
	@Test
	public void isUpMost() {
		assertTrue(Coordinate.isUpMost(0));
		assertTrue(Coordinate.isUpMost(1));
		assertTrue(Coordinate.isUpMost(7));
		assertFalse(Coordinate.isUpMost(23));
	}

	/**
	 * Method to test the expected behavior of the Method Coordinate.isDownMost()
	 */
	@Test
	public void isDownMost() {
		assertFalse(Coordinate.isDownMost(0));
		assertFalse(Coordinate.isDownMost(23));
		assertTrue(Coordinate.isDownMost(57));
		assertTrue(Coordinate.isDownMost(56));
	}
}