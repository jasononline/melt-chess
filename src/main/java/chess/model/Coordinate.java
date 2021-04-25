package chess.model;


/**
 * Helper class for converting the different representations of the square positions
 */
public class Coordinate {
    public static final String fileNames = "abcdefgh";
    public static final String rankNames = "12345678";

    /**
     * Convert squares index to 2d coordinate
     * @param index the position in the board.squares array
     * @return array containing {file, rank}
     */
    public static int[] fromIndex(int index) {
        int file = index % 8;
        int rank = index / 8;
        return new int[] {file, rank};
    }

    /**
     * Returns true if index lies on the "a" file
     * @param index the position in the board.squares array
     */
    public static boolean isLeftMost(int index) {
        return fromIndex(index)[0] == 0;
    }


    /**
     * Returns true if index lies on the "h" file
     * @param index the position in the board.squares array
     */
    public static boolean isRightMost(int index) {
        return fromIndex(index)[0] == 7;
    }


    /**
     * Returns true if index lies on the "8" rank
     * @param index the position in the board.squares array
     */
    public static boolean isUpMost(int index) {
        return fromIndex(index)[1] == 0;
    }


    /**
     * Returns true if index lies on the "1" rank
     * @param index the position in the board.squares array
     */
    public static boolean isDownMost(int index) {
        return fromIndex(index)[1] == 7;
    }


    /**
     * Returns True if index is any of the border squares
     * @param index the position in the board.squares array
     */
    public static boolean isOnBorder(int index) {
        return isUpMost(index) || isDownMost(index) || isLeftMost(index) || isRightMost(index);
    }

    /**
     * Converts square index to String representation of the coordinate
     * @param index position of the square
     * @return String representation of the coordinate
     */
    public static String toString(int index) {
        int[] coordinates = fromIndex(index);
        return fileNames.charAt(coordinates[0]) +"" + rankNames.charAt(coordinates[1]);
    }

}
