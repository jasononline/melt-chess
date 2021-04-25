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
    private int flag;

    /**
     * Construct Move
     * @param startSquare Integer representation starting square
     * @param targetSquare Integer representation of the target square
     */
    public Move(int startSquare, int targetSquare) {
        this.startSquare = startSquare;
        this.targetSquare = targetSquare;
        this.flag = 0;
    }

    /**
     * Construct Move setting a flag
     * @param startSquare Integer representation starting square
     * @param targetSquare Integer representation of the target square
     * @param flag set any of the flags 0 to 7
     */
    public Move(int startSquare, int targetSquare, int flag) {
        this(startSquare, targetSquare);
        this.flag = flag;
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
        String promotion;
        switch (flag) {
            case Move.PromoteToQueen:
                promotion = "Q";
                break;
            case Move.PromoteToBishop:
                promotion = "B";
                break;
            case Move.PromoteToKnight:
                promotion = "N";
                break;
            case Move.PromoteToRook:
                promotion = "R";
                break;
            default:
                promotion = "";
        }
        return Coordinate.toString(startSquare) + "-" + Coordinate.toString(targetSquare) + promotion;
    }

    /**
     * Checks whether this is the same as other move
     * @param other the other Move object
     * @return true if objects have same fields else false
     */
    public boolean equals(Object other) {
        if (other instanceof Move) {
            Move o = (Move) other;
            return startSquare == o.startSquare && targetSquare == o.targetSquare && flag == o.flag;
        } else {
            return super.equals(other);
        }
    }

}
