package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Contains methods to test the methods of the Piece class
 */
public class PieceTest {

    /**
     * useless test for the coverage (class is static)
     */
    @Test
    public void createPiece(){
        Piece piece = new Piece();
        assertEquals(piece, piece);
    }

    /**
     * Method to test the expected behavior of the Method Piece.isColor()
     */
    @Test
    public void isColor() {
        assertFalse(Piece.isColor(Piece.None, Piece.Black));
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
        String[] actual = new String[] {
                Piece.toString(Piece.King + Piece.Black),
                Piece.toString(Piece.Pawn + Piece.Black),
                Piece.toString(Piece.Knight + Piece.Black),
                Piece.toString(Piece.Bishop + Piece.Black),
                Piece.toString(Piece.Rook + Piece.Black),
                Piece.toString(Piece.Queen + Piece.Black),
                Piece.toString(Piece.Bishop + Piece.White),
                Piece.toString(Piece.Knight + Piece.White),
                Piece.toString(Piece.Pawn + Piece.White),
                Piece.toString(Piece.King + Piece.White),
                Piece.toString(Piece.Rook + Piece.White),
                Piece.toString(Piece.Queen + Piece.White),
        };

        String[] expected = new String[] {
                Piece.UTFSymbolKingBlack,
                Piece.UTFSymbolPawnBlack,
                Piece.UTFSymbolKnightBlack,
                Piece.UTFSymbolBishopBlack,
                Piece.UTFSymbolRookBlack,
                Piece.UTFSymbolQueenBlack,
                Piece.UTFSymbolBishopWhite,
                Piece.UTFSymbolKnightWhite,
                Piece.UTFSymbolPawnWhite,
                Piece.UTFSymbolKingWhite,
                Piece.UTFSymbolRookWhite,
                Piece.UTFSymbolQueenWhite,
        };

        for (int i=0;i<expected.length;i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}