package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void squaresFromFENString() {
        String fen = "kqrbnp2/8/7p/4p2N/3p3B/2P1P2R/7Q/7K";
        Board b = new Board(fen);
    }

    @Test
    public void makeMove() {
    }

    @Test
    public void positionToIndex() {
    }

    @Test
    public void testToString() {
    }

    @Test
    public void getPieceAt() {
    }

    @Test
    public void getPiecePositionsFor() {
    }
}