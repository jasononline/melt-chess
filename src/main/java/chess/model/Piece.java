package chess.model;

/**
 * A Chess Piece is represented as Integer value.
 * This class offers constants and static helper functions.
 */
public class Piece {

    public static final int None = 0;
    public static final int King = 1;
    public static final int Pawn = 2;
    public static final int Knight = 3;
    public static final int Bishop = 5;
    public static final int Rook = 6;
    public static final int Queen = 7;
    public static final int White = 8;
    public static final int Black = 16;
    public static final int typeMask = 0b00111;
    public static final int blackMask = 0b10000;
    public static final int whiteMask = 0b01000;
    public static final int colourMask = whiteMask | blackMask;


    /**
     * Returns true if piece is color
     * @param piece Integer representation of the piece
     * @param color Integer representation of the color
     */
    public static Boolean isColor(int piece, int color) {
        // TODO write tests
        // TODO write function
        return true;
    }


    /**
     * Returns the Integer value of color of the piece
     * @param piece Integer representation of the piece
     */
    public static Integer color(int piece) {
        // TODO write tests
        // TODO write function
        return 8;
    }


    /**
     * Returns the Integer value of the type of the piece
     * @param piece Integer representation of the piece
     */
    public static Integer pieceType(int piece) {
        // TODO write tests
        // TODO write function
        return 0;
    }


    /**
     * Returns String representation of a piece as used by FEN strings and the console client
     * @param piece Integer representation of the piece
     */
    public static String toString(int piece) {
        // TODO write tests
        // TODO write function
        return "K";
    }

}
