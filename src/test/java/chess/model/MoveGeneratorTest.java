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
     * Tests move generation on empty square
     */
    @Test
    public void emptySquareGeneration() {
        Board b = new Board();
        MoveGenerator g = new MoveGenerator(b);
        assertEquals(0, g.generateMovesStartingAt(0).size());
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
        assertTrue(expectedMoves.containsAll(generatedMoves));
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
        for (int expectedTarget : new int[]{1,3,8,12,24,28,33,35}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }

        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves = generator.generateKnightMoves(startSquare);
        assertTrue(generatedMoves.containsAll(expectedMoves));
        assertTrue(expectedMoves.containsAll(generatedMoves));
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
        int startSquare = 51;
        String fen = "8/8/8/6Q1/1r6/8/3B4/8";
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves;
        List<Move> expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{58,60,42,33,44,37}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }

        generatedMoves = generator.generateBishopMoves(startSquare);
        assertTrue(generatedMoves.containsAll(expectedMoves));
        assertTrue(expectedMoves.containsAll(generatedMoves));
    }


    /**
     * Test across move generation
     */
    @Test
    public void generateRookMoves() {
        int startSquare = 17;
        String fen = "8/8/1R6/8/8/1Q2r3/8/8";
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves;
        List<Move> expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{1,9,16,18,19,20,21,22,23,25,33}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }

        generatedMoves = generator.generateRookMoves(startSquare);
        assertTrue(generatedMoves.containsAll(expectedMoves));
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
        assertTrue(expectedMoves.containsAll(generatedMoves));
    }


    /**
     * Test queen move generation
     */
    @Test
    public void generateQueenMovesAlongBorder() {
        int startSquare = 59;
        Board board = new Board("8/8/8/8/8/3R4/2P1P3/k2Q4");
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> generatedMoves;
        List<Move> expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{51,56,57,58,60,61,62,63}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }
        generatedMoves = generator.generateQueenMoves(startSquare);
        assertTrue(generatedMoves.containsAll(expectedMoves));
        assertTrue(expectedMoves.containsAll(generatedMoves));
    }
}