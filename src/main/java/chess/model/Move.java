package chess.model;

/**
 * Container for a possible Move. Contains start and destination square
 * as well as flags for capturing, castling and piece exchange
 */
public class Move {

    public static final int None = 0;
    public static final int EnPassantCapture = 1;
    public static final int Castling = 2;
    public static final int PromoteToQueen = 3;
    public static final int PromoteToKnight = 4;
    public static final int PromoteToRook = 5;
    public static final int PromoteToBishop = 6;
    public static final int PawnTwoForward = 7;


    private int startSquare;
    private int targetSquare;

    /**
     * Construct Move
     * @param startSquare Integer representation starting square
     * @param targetSquare Integer representation of the target square
     */
    public Move(int startSquare, int targetSquare) {
        // TODO write tests
        // TODO write function
    }

    /**
     * Construct Move setting a flag
     * @param startSquare Integer representation starting square
     * @param targetSquare Integer representation of the target square
     * @param flag set any of the flags 0 to 7
     */
    public Move(int startSquare, int targetSquare, int flag) {
        // TODO write tests
        // TODO write function
    }


    /**
     * Returns the starting square
     */
    public int getStartSquare() {
        return startSquare;
    }

    /**
     * Returns the target square
     */
    public int getTargetSquare() {
        return targetSquare;
    }


    /**
     * Returns the string representation of the Move instance as used by the console client
     */
    public String toString() {
        // TODO write tests
        // TODO write function
        return "";
    }

}