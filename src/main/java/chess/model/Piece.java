package chess.model;

/**
 * A Chess Piece is represented as Integer value.
 * This class offers constants and static helper functions.
 */
public class Piece {

    public static final int None       = 0;
    public static final int King       = 1;
    public static final int Pawn       = 2;
    public static final int Knight     = 3;
    public static final int Bishop     = 4;
    public static final int Rook       = 5;
    public static final int Queen      = 6;
    public static final int White      = 8;
    public static final int Black      = 16;
    public static final int typeMask   = 0b00111;
    public static final int blackMask  = 0b10000;
    public static final int whiteMask  = 0b01000;
    public static final int colorMask = whiteMask | blackMask;

    public static final String UTFSymbols = " ♔♙♘♗♖♕♚♟♞♝♜♛";

    public static final String UTFSymbolNone        = " ";
    public static final String UTFSymbolKingWhite   = "♔";
    public static final String UTFSymbolPawnWhite   = "♙";
    public static final String UTFSymbolKnightWhite = "♘";
    public static final String UTFSymbolBishopWhite = "♗";
    public static final String UTFSymbolRookWhite   = "♖";
    public static final String UTFSymbolQueenWhite  = "♕";
    public static final String UTFSymbolKingBlack   = "♚";
    public static final String UTFSymbolPawnBlack   = "♟";
    public static final String UTFSymbolKnightBlack = "♞";
    public static final String UTFSymbolBishopBlack = "♝";
    public static final String UTFSymbolRookBlack   = "♜";
    public static final String UTFSymbolQueenBlack  = "♛";



    /**
     * @param piece Integer representation of the piece
     * @param color Integer representation of the color
     * @return Returns true if piece is color
     */
    public static Boolean isColor(int piece, int color) {
        return (piece & colorMask) == color;
    }


    /**
     * @param piece Integer representation of the piece
     * @return the Integer value of color of the piece
     */
    public static Integer getColor(int piece) {
        return piece & colorMask;
    }


    /**
     * @param piece Integer representation of the piece
     * @return the Integer value of the type of the piece
     */
    public static Integer getType(int piece) {
        return piece & typeMask;
    }


    /**
     * @param piece Integer representation of the piece
     * @return String representation of a piece as used by FEN strings and the console client
     */
    public static String toString(int piece) {
        int type = getType(piece);
        int position = getColor(piece) == White || type == 0 ? 0 : 6;
        position += type;
        return UTFSymbols.substring(position, position+1);
    }
}
