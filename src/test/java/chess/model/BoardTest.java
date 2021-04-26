package chess.model;

import java.util.ArrayList;
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
        String fen = "rn1qkb1r/ppppp1pp/4b3/1N1P2p1/5P2/2n1Q3/PPPP2PP/R1B1KBNR";
        Board b = new Board(fen);
        String goal = "8 ♜ ♞   ♛ ♚ ♝   ♜\n" +
                "7 ♟ ♟ ♟ ♟ ♟   ♟ ♟\n" +
                "6         ♝      \n" +
                "5   ♘   ♙     ♟  \n" +
                "4           ♙    \n" +
                "3     ♞   ♕      \n" +
                "2 ♙ ♙ ♙ ♙     ♙ ♙\n" +
                "1 ♖   ♗   ♔ ♗ ♘ ♖\n" +
                "  a b c d e f g h\n";
        String actual = b.toString();

        assertEquals(goal, b.toString());
    }

    @Test
    public void getPiecePositionsFor() {
        String fen = "kqr5/8/8/8/8/8/8/5RQK";
        Board b = new Board(fen);

        ArrayList<Integer> positions = b.getPiecePositionsFor(Piece.Black);
        for (int i=0;i<3;i++) assertTrue(positions.contains(i));

        positions = b.getPiecePositionsFor(Piece.White);
        for (int i=61;i<64;i++) assertTrue(positions.contains(i));
    }
}