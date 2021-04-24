package chess.model;


import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveGeneratorTest {

    /**
     * Tests for the rules of the pawn
     */
    @Test
    public void testPawnRules() {
        String fen = "8/5pP1/3p4/3PP3/8/8/8/8";
        Board board = new Board(fen);

        MoveGenerator generator = new MoveGenerator(board);
        ArrayList<Move> generatedMoves = generator.generateMoves();
        board.setTurnColor(Piece.Black);
        generator = new MoveGenerator(board);
        generatedMoves.addAll(generator.generateMoves());

        ArrayList<Move> expectedMoves = new ArrayList<>();
        // valid black piece moves
        expectedMoves.add(new Move(13, 21));
        expectedMoves.add(new Move(13, 29, Move.PawnTwoForward));
        expectedMoves.add(new Move(19, 28));
        // valid white piece moves
        expectedMoves.add(new Move(14, 6, Move.PromoteToQueen));
        expectedMoves.add(new Move(28, 19));
        expectedMoves.add(new Move(28, 20));
        // check generated vs expected
        assertEquals(generatedMoves.size(), expectedMoves.size());
        boolean found;
        for (Move expected : expectedMoves) {
            found = false;
            for (Move generated : generatedMoves) {
                if (expected.equals(generated)) found = true;
            }
            assertTrue(found);
        }

        // test en passant capture
        fen = "rnbqkbnr/ppp1pppp/8/3pP3/4P3/8/PPPPPPPP/RNBQKBNR";
        board = new Board(fen);
        board.setEnPassantSquare(19);
        Move expectedMove = new Move(28, 19, Move.EnPassantCapture);

        generator = new MoveGenerator(board);
        generatedMoves = generator.generateMoves();
        assertTrue(generatedMoves.contains(expectedMove));
    }

    @Test
    public void generateMoves() {
        // test pawn rules
        testPawnRules();
    }

    @Test
    public void isValid() {
    }
}