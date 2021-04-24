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

}