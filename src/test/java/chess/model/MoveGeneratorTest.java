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
        ArrayList<Move> generatedMoves = new ArrayList<>();
        for (int position : new int[]{27, 28, 14})
            generatedMoves.addAll(generator.generatePawnMoves(position));

        board.setTurnColor(Piece.Black);
        generator = new MoveGenerator(board);
        for (int position : new int[]{13, 19})
            generatedMoves.addAll(generator.generatePawnMoves(position));

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
        ArrayList<Move> generatedMoves = new ArrayList<>();
        generatedMoves.addAll(generator.generatePawnMoves(28));
        generatedMoves.addAll(generator.generatePawnMoves(53));
        assertTrue(generatedMoves.contains(expectedMove));
    }

    /**
     * Test MoveGenerator on empty board... for the coverage!
     */
    @Test
    public void generateMoves() {
        Board board = new Board();
        MoveGenerator generator = new MoveGenerator(board);
        ArrayList<Move> moves = generator.generateMoves();
        assertTrue(moves.isEmpty());
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
        ArrayList<Move> generatedMoves;
        ArrayList<Move> expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{16, 32, 18, 34, 43}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }

        generatedMoves = generator.generateBishopMoves(startSquare);
        assertTrue(expectedMoves.containsAll(generatedMoves));
    }


    /**
     * Test diagonal move generation
     */
    @Test
    public void generateRookMoves() {
        int startSquare = 41;
        String fen = "8/8/1R6/8/8/1Q2r3/8/8";
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);
        ArrayList<Move> generatedMoves;
        ArrayList<Move> expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{40, 49, 57, 42, 43, 44, 33, 25}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }

        generatedMoves = generator.generateRookMoves(startSquare);
        assertTrue(expectedMoves.containsAll(generatedMoves));
    }


    /**
     * Test Queen move generation
     */
    @Test
    public void generateQueenMoves() {
        int startSquare = 41;
        String fen = "8/8/1R6/8/K1b5/1Q2r3/8/3B4";
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);
        ArrayList<Move> generatedMoves;
        ArrayList<Move> expectedMoves = new ArrayList<>();
        for (int expectedTarget : new int[]{25,33, 34, 40, 42, 43, 44, 48, 49, 50, 57}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }

        generatedMoves = generator.generateQueenMoves(startSquare);
        assertTrue(generatedMoves.containsAll(expectedMoves));

    }


    /**
     * Test King move generation
     */
    @Test
    public void generateKingMoves() {
        int startSquare = 60;
        String fen = "8/3k4/8/8/8/8/8/4K3";
        // test white King first
        Board board = new Board(fen);
        MoveGenerator generator = new MoveGenerator(board);
        ArrayList<Move> generatedMoves;
        ArrayList<Move> expectedMoves = new ArrayList<>();
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


    @Test
    public void generateKingMovesCastling() {
        String fen = "r3kq1r/8/8/8/8/8/8/R3K2R";
        // test white king first
        Board board = new Board(fen);
        board.forbidCastlingA1();
        MoveGenerator generator = new MoveGenerator(board);
        ArrayList<Move> generatedMoves;
        ArrayList<Move> expectedMoves = new ArrayList<>();
        // expected moves for white
        int startSquare = 60;/*
        for (int expectedTarget : new int[]{51, 52, 53, 59, 61}) {
            expectedMoves.add(new Move(startSquare, expectedTarget));
        }
        expectedMoves.add(new Move(startSquare, 62, Move.Castling));*/
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

    @Test
    public void isValid() {
    }
}