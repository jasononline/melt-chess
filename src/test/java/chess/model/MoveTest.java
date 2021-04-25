package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {

    @Test
    public void getStartSquare() {
        int start = 0;
        int target = 63;
        Move m = new Move(start, target);
        assertEquals(m.getStartSquare(), start);
    }

    @Test
    public void getTargetSquare() {
        int start = 0;
        int target = 63;
        Move m = new Move(start, target);
        assertEquals(m.getTargetSquare(), target);
    }

    @Test
    public void equals() {
        Move m1 = new Move(1, 2, 1);
        Move m2 = new Move(1, 2, 1);
        Move m3 = new Move(1, 3, 2);
        assertEquals(m1, m2);
        assertEquals(m2, m1);
        assertNotEquals(m1, m3);
    }

    @Test
    public void testToString() {
        Move[] testMoves = new Move[] {
                new Move(0, 1),
                new Move(12, 4, Move.PromoteToQueen),
                new Move(12, 4, Move.PromoteToBishop),
                new Move(12, 4, Move.PromoteToKnight),
                new Move(12, 4, Move.PromoteToRook),
                new Move(0, 63),

        };

        String[] expectedStrings = new String[] {
                "a8-b8", "e7-e8Q", "e7-e8B", "e7-e8N", "e7-e8R", "a7-h8"
        };
        for (int i=0; i< testMoves.length; i++) {
            assertEquals(expectedStrings[i], testMoves[i].toString());
        }
    }
}