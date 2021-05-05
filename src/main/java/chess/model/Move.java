package chess.model;

/**
 * Container for a possible Move. Contains start and destination square
 * as well as flags for capturing, castling and piece exchange
 */
public class Move {

    public static final int None = 0;
    public static final int EnPassantCapture = 1;
    public static final int PawnTwoForward = 2;
    public static final int PromoteToKnight = Piece.Knight; // 3
    public static final int PromoteToBishop = Piece.Bishop; // 4
    public static final int PromoteToRook = Piece.Rook;     // 5
    public static final int PromoteToQueen = Piece.Queen;   // 6
    public static final int Castling = 7;


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
     * @return the starting square
     */
    public int getStartSquare() {
        return startSquare;
    }

    /**
     * @return the target square
     */
    public int getTargetSquare() {
        return targetSquare;
    }

    /**
     * @return returns the move flag
     */
    public int getFlag() {
        return flag;
    }

    /**
     * Set the flag field
     * @param flag the flag value
     */
    public void setFlag(int flag) { this.flag = flag;}


    /**
     * @return the string representation of the Move instance as used by the console client
     */
    @Override
    public String toString() {
        // TODO write tests for Move.toString
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
    @Override
    public boolean equals(Object other) {
        if (other instanceof Move) {
            Move o = (Move) other;
            return startSquare == o.startSquare && targetSquare == o.targetSquare && flag == o.flag;
        } else {
            return super.equals(other);
        }
    }

    /**
     * Calculates arbitrary hash code to accommodate pmd rules
     * @return sum of start-, targetSquare and the flag
     */
    @Override
    public int hashCode() {
        return startSquare + targetSquare + flag;
    }
}
