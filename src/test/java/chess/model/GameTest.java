package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 *  Tests for the Game class
 */
public class GameTest {

    /**
     * Test undo move
     */
    @Test
    public void undoMove() {
        Game game = new Game();
        Move movePawn = new Move(52, 36, Move.PawnTwoForward);
        game.attemptMove(movePawn);
        game.undoMove();
        Board currentPosition = game.getCurrentPosition();
        assertEquals(Piece.Pawn + Piece.White, currentPosition.getPieceAt(52));
    }



    /**
     * Test the attemptMovePiece method of the game
     * It should check for *all* rules!
     */
    @Test
    public void attemptMove() {
        Game game = new Game();
        Board startingPosition = game.getCurrentPosition();
        Move movePawn = new Move(52, 37, Move.PawnTwoForward);
        assertFalse(game.attemptMove(movePawn));
    }


    /**
     * Test the check for checkmate
     */
    @Test
    public void checkWinConditionCheckmate() {
        Board checkmatePosition = new Board("8/8/8/8/8/4q3/8/1q2K3");
        Board notCheckmatePosition = new Board("8/8/8/8/8/4r3/3Q4/1q2K3");

        Game game = new Game();
        System.out.println("Test win condition:");
        System.out.println(checkmatePosition);
        assertEquals(Game.CHECKMATE, game.checkWinCondition(checkmatePosition));
        System.out.println("Test win condition:");
        System.out.println(notCheckmatePosition);
        assertEquals(0, game.checkWinCondition(notCheckmatePosition));
    }

    /**
     * Test the check for remis
     */
    @Test
    public void checkWinConditionRemis() {
        Board simpleRemisPosition = new Board("8/8/8/8/8/3q1q2/8/4K3");
        Game game = new Game();
        System.out.println("Test win condition (remis):");
        System.out.println(simpleRemisPosition);
        assertEquals(Game.REMIS, game.checkWinCondition(simpleRemisPosition));
    }


    /**
     * Tests for the adding of flags for user input moves
     */
    @Test
    public void addFlagEnPassantCapture() {

        String fen = "8/8/8/8/p7/8/1P6/8";
        Game game = new Game(fen);
        Move m1 = new Move(49, 33, Move.PawnTwoForward);
        Move m2 = new Move(32, 41);
        // make move that triggers en passant possibility
        System.out.println(game.getCurrentPosition());
        System.out.println("Turn: " +game.getCurrentPosition().getTurnColor());
        game.attemptMove(m1);
        System.out.println(game.getCurrentPosition());
        System.out.println("Turn: " +game.getCurrentPosition().getTurnColor());
        System.out.println("En Passant Square: " + game.getCurrentPosition().getEnPassantSquare());
        game.addFlag(m2);
        assertEquals(Move.EnPassantCapture, m2.getFlag());
    }


    /**
     * Tests for the adding of flags for user input moves
     */
    @Test
    public void addFlagCastling() {

        String fen = "rnbqkbnr/pppppppp/8/8/2P5/1QNPPN2/PP1BBPPP/R3K2R";
        Game game = new Game(fen);
        Move m1 = new Move(60, 62);
        Move m2 = new Move(60, 58);
        game.addFlag(m1);
        game.addFlag(m2);

        assertEquals(Move.Castling, m1.getFlag());
        assertEquals(Move.Castling, m2.getFlag());
    }

}