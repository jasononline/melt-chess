package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains methods to test the methods of the Piece class
 */
public class PieceTest {

    /**
     * Method to test the expected behavior of the Method Piece.isColor()
     */
    @Test
    public void isColor() {
        assertFalse(Piece.isColor(Piece.None, Piece.Black));
        assertFalse(Piece.isColor(Piece.None, Piece.White));
        for (int type=1; type < 8; type++) {
            assertFalse(Piece.isColor(type + Piece.White, Piece.Black));
            assertFalse(Piece.isColor(type + Piece.Black, Piece.White));
            assertTrue(Piece.isColor(type + Piece.White, Piece.White));
            assertTrue(Piece.isColor(type + Piece.Black, Piece.Black));
        }
    }

    /**
     * Method to test the expected behavior of the Method Piece.getColor()
     */
    @Test
    public void getColor() {
        assertEquals(Piece.getColor(Piece.None), 0);
        assertEquals(Piece.getColor(Piece.King + Piece.White), Piece.White);
        assertEquals(Piece.getColor(Piece.King + Piece.Black), Piece.Black);
        assertEquals(Piece.getColor(Piece.Queen + Piece.White), Piece.White);
        assertEquals(Piece.getColor(Piece.Queen + Piece.Black), Piece.Black);
    }

    /**
     * Method to test the expected behavior of the Method Piece.getType()
     */
    @Test
    public void getType() {
        assertEquals(Piece.getType(Piece.None), 0);
        assertEquals(Piece.getType(Piece.King + Piece.White), Piece.King);
        assertEquals(Piece.getType(Piece.King + Piece.Black), Piece.King);
        assertEquals(Piece.getType(Piece.Queen + Piece.White), Piece.Queen);
        assertEquals(Piece.getType(Piece.Queen + Piece.Black), Piece.Queen);
    }

    /**
     * Method to test the expected behavior of the Method Piece.testToString()
     */
    @Test
    public void testToString() {
        assertEquals(Piece.toString(Piece.King + Piece.Black), Piece.UTFSymbolKingBlack);
        assertEquals(Piece.toString(Piece.King + Piece.White), Piece.UTFSymbolKingWhite);
        assertEquals(Piece.toString(Piece.Pawn + Piece.Black), Piece.UTFSymbolPawnBlack);
        assertEquals(Piece.toString(Piece.Pawn + Piece.White), Piece.UTFSymbolPawnWhite);
        assertEquals(Piece.toString(Piece.Knight + Piece.Black), Piece.UTFSymbolKnightBlack);
        assertEquals(Piece.toString(Piece.Knight + Piece.White), Piece.UTFSymbolKnightWhite);
        assertEquals(Piece.toString(Piece.Bishop + Piece.Black), Piece.UTFSymbolBishopBlack);
        assertEquals(Piece.toString(Piece.Bishop + Piece.White), Piece.UTFSymbolBishopWhite);
        assertEquals(Piece.toString(Piece.Rook + Piece.Black), Piece.UTFSymbolRookBlack);
        assertEquals(Piece.toString(Piece.Rook + Piece.White), Piece.UTFSymbolRookWhite);
        assertEquals(Piece.toString(Piece.Queen + Piece.Black), Piece.UTFSymbolQueenBlack);
        assertEquals(Piece.toString(Piece.Queen + Piece.White), Piece.UTFSymbolQueenWhite);
        assertEquals(Piece.toString(Piece.None), Piece.UTFSymbolNone);
    }
}