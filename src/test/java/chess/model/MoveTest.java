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
    public void testToString() {
    }
}