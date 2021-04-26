package chess.model;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the MoveGenerator class.
 * Mostly tests on piece movement, neglecting rules for check
 */
public class MoveGeneratorTest {


    /**
     * Swap colors test
     */
    @Test
    public void swapColors() {
        Board b = new Board();
        MoveGenerator g = new MoveGenerator(b);
        g.swapColors();
        assertEquals(Piece.Black, g.getTeamColor());
    }


    /**
     * Test teamColor getter
     */
    @Test
    public void getTeamColor() {
        Board b = new Board();
        MoveGenerator g = new MoveGenerator(b);
        assertEquals(Piece.White, g.getTeamColor());
    }

    /**
     * Tests for the rules of the pawn
     */
    @Test
    public void testPawnRules() {
        String fen = "8/5pP1/3p4/3PP3/8/8/8/8";
        Board board = new Board(fen);

        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves = new ArrayList<>();
        for (int position : new int[]{27, 28, 14})
            generatedMoves.addAll(generator.generatePawnMoves(position));

        board.setTurnColor(Piece.Black);
        generator = new MoveGenerator(board);
        for (int position : new int[]{13, 19})
            generatedMoves.addAll(generator.generatePawnMoves(position));

        List<Move> expectedMoves = new ArrayList<>();
        // valid black piece moves
        expectedMoves.add(new Move(13, 21));
        expectedMoves.add(new Move(13, 29, Move.PawnTwoForward));
        expectedMoves.add(new Move(19, 28));
        // valid white piece moves
        expectedMoves.add(new Move(14, 6, Move.PromoteToQueen));
        expectedMoves.add(new Move(28, 19));
        expectedMoves.add(new Move(28, 20));
        // check generated vs expected
        assertTrue(expectedMoves.containsAll(generatedMoves));
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
        List<Move> generatedMoves = new ArrayList<>();
        generatedMoves.addAll(generator.generatePawnMoves(28));
        generatedMoves.addAll(generator.generatePawnMoves(53));
        assertTrue(generatedMoves.contains(expectedMove));
    }


    /**
     * Test MoveGenerator on initial board... for the coverage!
     */
    @Test
    public void generateMoves() {
        String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> moves = generator.generateMoves();
        // expect 16 possible pawn moves + 4 possible knight moves for white
        assertEquals(20, moves.size());
    }


    /**
     * Simple test of move generation for the knight standing in a corner
     */
    @Test
    public void generateKnightMovesBorder() {
        String fen = "N7/8/8/8/8/8/8/7N";
        Board board = new Board(fen);

        List<Move> expectedMoves = new ArrayList<>();
        expectedMoves.add(new Move(63, 46));
        expectedMoves.add(new Move(63, 53));
        expectedMoves.add(new Move(0, 10));
        expectedMoves.add(new Move(0, 17));


        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves = generator.generateKnightMoves(63);
        generatedMoves.addAll(generator.generateKnightMoves(0));
        assertTrue(generatedMoves.containsAll(expectedMoves));
    }


    /**
     * Test move generation for the knight without blocking squares
     */
    @Test
    public void generateKnightMovesNonBlocking() {
        String fen = "8/8/2N5/8/8/8/8/8";
        int startSquare = 18;
        Board board = new Board(fen);


        List<Move> expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{1,3,8,12,24,28,33,35})
            expectedMoves.add(new Move(startSquare, expectedTarget));

        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves = generator.generateKnightMoves(startSquare);
        assertTrue(generatedMoves.containsAll(expectedMoves));
    }


    /**
     * Test move generation for the knight with blocking pieces
     */
    @Test
    public void generateKnightMovesBlocking() {
        String fen = "3P4/P3P3/2N5/P3P3/1P1P4/8/8/8";
        int startSquare = 18;
        Board board = new Board(fen);

        Move expectedMove = new Move(startSquare, 1);

        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves = generator.generateKnightMoves(startSquare);
        assertTrue(generatedMoves.contains(expectedMove));
        assertEquals(generatedMoves.size(), 1);
    }


    /**
     * Test diagonal move generation
     */
    @Test
    public void generateBishopMoves() {
        int startSquare = 25;
        String fen = "8/3R4/8/1Q6/8/3r4/8/8";
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves;
        List<Move> expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{16, 32, 18, 34, 43}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }

        generatedMoves = generator.generateBishopMoves(startSquare);
        assertTrue(expectedMoves.containsAll(generatedMoves));
    }


    /**
     * Test across move generation
     */
    @Test
    public void generateRookMoves() {
        int startSquare = 41;
        String fen = "8/8/1R6/8/8/1Q2r3/8/8";
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves;
        List<Move> expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{40, 49, 57, 42, 43, 44, 33, 25}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }

        generatedMoves = generator.generateRookMoves(startSquare);
        assertTrue(expectedMoves.containsAll(generatedMoves));
    }


    /**
     * Test queen move generation
     */
    @Test
    public void generateQueenMoves() {
        int startSquare = 41;
        String fen = "8/8/1R6/8/K1b5/1Q2r3/8/3B4";
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves;
        List<Move> expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{25,33, 34, 40, 42, 43, 44, 48, 49, 50, 57}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }

        generatedMoves = generator.generateQueenMoves(startSquare);
        assertTrue(generatedMoves.containsAll(expectedMoves));

    }


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
        for (int expectedTarget : new int[]{51, 52, 53, 59, 61}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }
        generatedMoves = generator.generateKingMoves(startSquare);

        // test black King
        board.setTurnColor(Piece.Black);
        startSquare = 11;
        generator = new MoveGenerator(board);
        for (int expectedTarget : new int[]{2, 3, 4, 10, 12, 18, 19, 20}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }
        generatedMoves.addAll(generator.generateKingMoves(startSquare));
        assertTrue(generatedMoves.containsAll(expectedMoves));
    }

    /**
     * Test king castling
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
    }
}