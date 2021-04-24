package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

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

    @Test
    public void color() {
    }

    @Test
    public void pieceType() {
    }

    @Test
    public void testToString() {
    }
}