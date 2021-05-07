package chess.model;

/**
 * Helper class for converting the different representations of the square
 * positions
 */
public class Coordinate {
	public static final String fileNames = "abcdefgh";
	public static final String rankNames = "87654321";

	/**
	 * Convert squares index to 2d coordinate
	 * 
	 * @param index the position in the board.squares array
	 * @return array containing {file, rank}
	 */
	public static int[] fromIndex(int index) {
		int file = index % 8;
		int rank = index / 8;
		return new int[] { file, rank };
	}

	/**
	 * Convert 2d coordinate ot squares index
	 * 
	 * @param position the position in the 2d coordinates (e7)
	 * @return the position in the board.squares array
	 */
	public static int toIntex(String position) {
		int file = fileNames.indexOf(String.valueOf(position.charAt(0)));
		int rank = 8 - Integer.parseInt(String.valueOf(position.charAt(1)));
		return rank * 8 + file;
	}


	/**
	 * Convert 2d integer coordinate to square index
	 * @param file the file of the coordinate (a:0, b:1, .., h:7)
	 * @param rank the rank of the coordinate (7:0, 6:1, ..., 0:7)
	 * @return the index for the Board.squares array
	 */
	public static int toIndex(int file, int rank) {
		return rank * 8 + file;
	}


	/**
	 * @param index the position in the board.squares array
	 * @return Returns true if index lies on the "a" file
	 */
	public static boolean isOnLeftBorder(int index) {
		return fromIndex(index)[0] == 0;
	}

	/**
	 * @param index the position in the board.squares array
	 * @return Returns true if index lies on the "h" file
	 */
	public static boolean isOnRightBorder(int index) {
		return fromIndex(index)[0] == 7;
	}

	/**
	 * @param index the position in the board.squares array
	 * @return Returns true if index lies on the "8" rank
	 */
	public static boolean isOnUpperBorder(int index) {
		return fromIndex(index)[1] == 0;
	}

	/**
	 * @param index the position in the board.squares array
	 * @return Returns true if index lies on the "1" rank
	 */
	public static boolean isOnLowerBorder(int index) {
		return fromIndex(index)[1] == 7;
	}

	/**
	 * @param index the position in the board.squares array
	 * @return Returns True if index is any of the border squares
	 */
	public static boolean isOnBorder(int index) {
		return isOnUpperBorder(index) || isOnLowerBorder(index) || isOnLeftBorder(index) || isOnRightBorder(index);
	}

	/**
	 * Return true if index is any of the border squares towards direction
	 * 
	 * @param index     the position in the board.squares array
	 * @param direction in which the piece is currently moving
	 * @return True if index is any of the border squares towards direction
	 */
	public static boolean isOnBorderTowards(int index, int direction) {
		switch (direction) {
		case MoveGenerator.UP:
			return isOnUpperBorder(index);
		case MoveGenerator.RIGHT:
			return isOnRightBorder(index);
		case MoveGenerator.DOWN:
			return isOnLowerBorder(index);
		case MoveGenerator.LEFT:
			return isOnLeftBorder(index);
		}
		return isOnBorder(index);
	}

	/**
	 * Converts square index to String representation of the coordinate
	 * 
	 * @param index position of the square
	 * @return String representation of the coordinate
	 */
	public static String toString(int index) {
		int[] coordinates = fromIndex(index);
		return fileNames.charAt(coordinates[0]) + "" + rankNames.charAt(coordinates[1]);
	}

}
