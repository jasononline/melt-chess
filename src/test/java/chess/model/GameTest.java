package chess.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 *  Tests for the Game class
 */
class GameTest {

    /**
     * Test undo move
     */
    @Test
    void undoMove() {
        Game game = new Game();
        Board startingPosition = game.getCurrentPosition();
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
    void attemptMove() {
        Game game = new Game();
        Board startingPosition = game.getCurrentPosition();
        Move movePawn = new Move(52, 37, Move.PawnTwoForward);
        assertFalse(game.attemptMove(movePawn));
    }


    /**
     * Test the check for checkmate
     */
    @Test
    void checkWinConditionCheckmate() {
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
    void checkWinConditionRemis() {
        Board simpleRemisPosition = new Board("8/8/8/8/8/3q1q2/8/4K3");
        Game game = new Game();
        System.out.println("Test win condition (remis):");
        System.out.println(simpleRemisPosition);
        assertEquals(Game.REMIS, game.checkWinCondition(simpleRemisPosition));
    }
}