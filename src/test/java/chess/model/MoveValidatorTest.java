package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveValidatorTest {

    public String fenPosition = "bkr5/1q6/3r4/1b2n3/4P3/3K1Nq1/2PPP3/8";


    /**
     * Test for checkCheck
     */
    @Test
    public void validateCheckCheck() {
        Board board = new Board(fenPosition);
        int expectedNumChecksWhite = 3;
        int expectedNumChecksBlack = 0;

        assertEquals(expectedNumChecksWhite, MoveValidator.checkCheck(board, Piece.White));
        assertEquals(expectedNumChecksBlack, MoveValidator.checkCheck(board, Piece.Black));
    }
}