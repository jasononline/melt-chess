package chess.model;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Pawn move generation rules
 */
public class MoveGeneratorTestPawn {


    /**
     * Test the promotion of a pawn
     */
    @Test
    public void pawnPromotion() {
        Board board = new Board("3r1r2/4P3/8/8/8/8/4p3/8");
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> moves = generator.generateMovesStartingAt(12);
        generator.swapColors();
        moves.addAll(generator.generateMovesStartingAt(52));
        assertEquals(16, moves.size());
    }


    /**
     * Tests for the rules of the pawn
     */
    @Test
    public void testPawnRules() {
        String fen = "8/5pP1/3p4/3PP3/8/8/8/8";
        Board board = new Board(fen);

        List<Move> generatedMoves = new ArrayList<>();
        for (int position : new int[]{27, 28, 14}) {
            generatedMoves.addAll(MoveGeneratorPawn.generatePawnMoves(board, position));
        }

        board.setTurnColor(Piece.Black);
        for (int position : new int[]{13, 19}) {
            generatedMoves.addAll(MoveGeneratorPawn.generatePawnMoves(board, position));
        }

        List<Move> expectedMoves = new ArrayList<>();
        // valid black piece moves
        expectedMoves.add(new Move(13, 21));
        expectedMoves.add(new Move(13, 29, Move.PawnTwoForward));
        expectedMoves.add(new Move(19, 28));
        // valid white piece moves
        expectedMoves.add(new Move(14, 6, Move.PromoteToQueen));
        expectedMoves.add(new Move(14, 6, Move.PromoteToRook));
        expectedMoves.add(new Move(14, 6, Move.PromoteToBishop));
        expectedMoves.add(new Move(14, 6, Move.PromoteToKnight));
        expectedMoves.add(new Move(28, 19));
        expectedMoves.add(new Move(28, 20));
        // check generated vs expected
        assertTrue(expectedMoves.containsAll(generatedMoves));
        assertTrue(generatedMoves.containsAll(expectedMoves));
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

        List<Move> generatedMoves = new ArrayList<>();
        generatedMoves.addAll(MoveGeneratorPawn.generatePawnMoves(board,28));
        generatedMoves.addAll(MoveGeneratorPawn.generatePawnMoves(board,53));
        assertTrue(generatedMoves.contains(expectedMove));

        System.out.println(board);
        board = board.makeMove(expectedMove);
        System.out.println("Board after " +expectedMove);
        System.out.println(board);
        assertEquals(Piece.None, board.getPieceAt(27));
        assertEquals(Piece.Pawn+Piece.White, board.getPieceAt(19));
    }
}
