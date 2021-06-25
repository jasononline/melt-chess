package chess.model;

import chess.cli.Cli;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Contains methods to test the methods of the Move class
 */
public class MoveTest {

    /**
     * Method to test equals and hash
     */
    @Test
    public void getEqualsHash() {

        Move m1 = new Move(0, 1, 2);
        Move m2 = new Move(0, 1, 2);
        Move m3 = new Move(1, 1, 2);
        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
        assertNotEquals(m1, m3);
        assertNotEquals(m1.hashCode(), m3.hashCode());
        assertNotEquals(m1, "wad");

    }



    /**
     * Method to test the expected behavior of the Method Move.getStartSquare()
     */
    @Test
    public void getStartSquare() {
        int start = 0;
        int target = 63;
        Move m = new Move(start, target);
        assertEquals(m.getStartSquare(), start);
    }

    /**
     * Method to test the expected behavior of the Method Move.getTargetSquare()
     */
    @Test
    public void getTargetSquare() {
        int start = 0;
        int target = 63;
        Move m = new Move(start, target);
        assertEquals(m.getTargetSquare(), target);
    }

    /**
     * Method to test the expected behavior of the Method Move.equals()
     */
    @Test
    public void equals() {
        Move m1 = new Move(1, 2, 1);
        Move m2 = new Move(1, 2, 1);
        Move m3 = new Move(1, 3, 2);
        assertEquals(m1, m2);
        assertEquals(m2, m1);
        assertNotEquals(m1, m3);
    }

    /**
     * Method to test the expected behavior of the Method Move.toString()
     */
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
                "a8-b8", "e7-e8Q", "e7-e8B", "e7-e8N", "e7-e8R", "a8-h1"
        };
        for (int i=0; i< testMoves.length; i++) {
            assertEquals(expectedStrings[i], testMoves[i].toString());
        }
    }

    /**
     * Method to test the expected behavior of the Method Cli.parseUserMoveInput()
     */
    @Test
    public void parseMoveFromString() {
        Game game = new Game();
        assertEquals(Move.parseUserMoveInput("c2-c4", game), new Move(50, 34, Move.PawnTwoForward));
        assertEquals(Move.parseUserMoveInput("c2-h4", game), new Move(50, 39, Move.PawnTwoForward));
        assertEquals(Move.parseUserMoveInput("a8-h1Q", game), new Move(0, 63, Move.PromoteToQueen));
        assertEquals(Move.parseUserMoveInput("h1-a8R",game), new Move(63, 0, Move.PromoteToRook));
    }
}