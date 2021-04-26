package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains methods to test the methods of the MoveValidator class
 */
public class MoveValidatorTest {

    public String fenPosition = "bkr5/1q6/3r4/1b2n3/4P3/3K1Nq1/2PPP3/8";


    /**
     * Test for checkCheck
     */
    @Test
    public void CheckCheck() {
        Board board = new Board(fenPosition);
        int expectedNumChecksWhite = 2;
        int expectedNumChecksBlack = 0;

        assertEquals(expectedNumChecksWhite, MoveValidator.checkCheck(board, Piece.White));
        assertEquals(expectedNumChecksBlack, MoveValidator.checkCheck(board, Piece.Black));
    }


    /**
     * Test if MoveValidator returns false for illegal move
     */
    @Test
    public void validateIllegalMove() {
        Move illegalMove = new Move(51, 35, Move.PawnTwoForward);
        Board board = new Board(fenPosition);
        assertFalse(MoveValidator.validateMove(board, illegalMove));
    }


    /**
     * Tests if MoveValidator returns false if after move the king is still in check
     * and if returns true if a move protects the king if it was in check before
     */
    @Test
    public void validateMoveStaysInCheck(){
        String fen = "8/4q3/8/8/8/8/3B4/4K3";
        Board board = new Board(fen);
        Move notProtectingMove = new Move(51, 42);
        Move protectingMove = new Move(51, 44);
        assertFalse(MoveValidator.validateMove(board, notProtectingMove));
        assertTrue(MoveValidator.validateMove(board, protectingMove));
    }


    /**
     * Test if MoveValidator returns false if a move puts own king into check
     */
    @Test
    public void validateMovePutsInCheck() {
        // protecting piece moves away
        String fen = "8/4q3/8/8/8/8/4B3/4K3";
        Board board = new Board(fen);
        Move putsInCheckMove = new Move(52, 43);
        assertFalse(MoveValidator.validateMove(board, putsInCheckMove));

        // king moves into check
        fen = "8/4q3/8/8/8/8/8/3K4";
        board = new Board(fen);
        Move movesIntoCheck = new Move(59, 60);
        assertFalse(MoveValidator.validateMove(board, movesIntoCheck));
    }

    /**
     * Test the various castling positions.
     * Expects white king to *NOT* be able to castle any direction.
     * @param fen initial board position for test
     */
    private void validateMoveCastling(String fen) {
        Board board = new Board(fen);
        Move castlingLeft = new Move(60, 58, Move.Castling);
        Move castlingRight = new Move(60, 62, Move.Castling);
        assertFalse(MoveValidator.validateMove(board, castlingLeft));
        assertFalse(MoveValidator.validateMove(board, castlingRight));
    }


    /**
     * Test for castling rules:
     * Castling is forbidden if:
     *   - King is in check
     */
    @Test
    public void validateMoveCastlingWhileKingInCheck() {
        String fen = "8/4q3/8/8/8/8/8/R3K2R";
        validateMoveCastling(fen);
    }


    /**
     * Test for castling rules:
     * Castling is forbidden if:
     *   - Any square between king and rook is under attack
     */
    @Test
    public void validateMoveCastlingWhileSquareUnderAttack() {
        String fen = "8/1q3q2/8/8/8/8/8/R3K2R";
        validateMoveCastling(fen);
    }


    /**
     * Test for castling rules:
     * Castling is forbidden if:
     *   - King is in check after castling
     */
    @Test
    public void validateMoveCastlingKingInCheckAfterwards() {
        String fen = "8/b1p3p1/8/8/8/b7/8/R3K2R";
        validateMoveCastling(fen);
    }


    /**
     * Test for legal castling
     */
    @Test
    public void validateMoveCastlingKing() {
        String fen = "8/2p3p1/8/8/8/8/8/R3K2R";
        Board board = new Board(fen);
        Move castlingLeft = new Move(60, 58, Move.Castling);
        Move castlingRight = new Move(60, 62, Move.Castling);
        assertTrue(MoveValidator.validateMove(board, castlingLeft));
        assertTrue(MoveValidator.validateMove(board, castlingRight));
    }
}