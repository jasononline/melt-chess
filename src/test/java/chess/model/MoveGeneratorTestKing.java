package chess.model;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test King move generation
 */
public class MoveGeneratorTestKing {


    /**
     * Test king move generation
     */
    @Test
    public void generateKingMoves() {
        int startSquare = 60;
        String fen = "8/3k4/8/8/8/8/8/4K3";
        // test white King first
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves;
        List<Move> expectedMoves = new ArrayList<>();
        // reminder: castling moves are also found, even though there are no rooks
        // that's because we don't validate the moves
        expectedMoves.add(new Move(60, 58, Move.Castling));
        expectedMoves.add(new Move(60, 62, Move.Castling));
        for (int expectedTarget : new int[]{51, 52, 53, 59, 61}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }
        generatedMoves = generator.generateKingMoves(startSquare);
        assertTrue(generatedMoves.containsAll(expectedMoves));
        assertTrue(expectedMoves.containsAll(generatedMoves));

        // test black King
        board.setTurnColor(Piece.Black);
        startSquare = 11;
        generator = new MoveGenerator(board);
        expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{2, 3, 4, 10, 12, 18, 19, 20}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }
        generatedMoves = generator.generateKingMoves(startSquare);
        assertTrue(generatedMoves.containsAll(expectedMoves));
        assertTrue(expectedMoves.containsAll(generatedMoves));
    }


    /**
     * Test king castling rules
     */
    @Test
    public void generateKingMovesCastling() {
        String fen = "r3kq1r/8/8/8/8/8/8/R3K2R";
        // test white king first
        Board board = new Board(fen);
        board.forbidCastlingA1();
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves;
        List<Move> expectedMoves = new ArrayList<>();
        // expected moves for white
        int startSquare = 60;
        for (int expectedTarget : new int[]{51, 52, 53, 59, 61}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }
        expectedMoves.add(new Move(startSquare, 62, Move.Castling));
        generatedMoves = generator.generateKingMoves(startSquare);

        // now test black king
        board.setTurnColor(Piece.Black);
        startSquare = 4;
        generator = new MoveGenerator(board);
        // expected moves for black
        for (int expectedTarget : new int[]{3, 11, 12, 13}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }
        expectedMoves.add(new Move(startSquare, 2, Move.Castling));
        generatedMoves.addAll(generator.generateKingMoves(startSquare));

        assertTrue(generatedMoves.containsAll(expectedMoves));
        assertTrue(expectedMoves.containsAll(generatedMoves));
    }


    /**
     * Expect no possible moves for either king at starting position
     */
    @Test
    public void testKingStartMovesGeneration() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);

        System.out.println(board);
        System.out.println("Expect no moves for the kings..");
        if (generator.getTeamColor() != Piece.Black)
            generator.swapColors();
        List<Move> moves = generator.generateMovesStartingAt(4);
        assertEquals(0, moves.size());
        if (generator.getTeamColor() != Piece.White)
            generator.swapColors();
        moves = generator.generateMovesStartingAt(60);
        assertEquals(0, moves.size());

    }
}
