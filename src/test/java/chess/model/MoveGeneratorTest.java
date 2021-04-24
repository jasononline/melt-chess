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
                if (expected.equals(generated)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }


    }

    /**
     * Tests for the en passant capture rule of the pawn
     */
    @Test
    public void testPawnEnPassantCapture() {
        // test en passant capture
        String fen = "rnbqkbnr/ppp1pppp/8/3pP3/4P3/8/PPPPPPPP/RNBQKBNR";
        Board board = new Board(fen);
        board.setEnPassantSquare(19);
        Move expectedMove = new Move(28, 19, Move.EnPassantCapture);

        MoveGenerator generator = new MoveGenerator(board);
        ArrayList<Move> generatedMoves = generator.generateMoves();
        assertTrue(generatedMoves.contains(expectedMove));
    }

    /**
     * Test MoveGenerator on empty board... for the coverage!
     */
    @Test
    public void generateMoves() {
        // test pawn rules
        Board b = new Board();
        MoveGenerator generator = new MoveGenerator(b);
        ArrayList<Move> moves = generator.generateMoves();
        assertTrue(moves.isEmpty());
    }

    @Test
    public void isValid() {
    }
}