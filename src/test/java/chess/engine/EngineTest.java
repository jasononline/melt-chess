package chess.engine;

import chess.model.Move;
import chess.model.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the engine
 */
public class EngineTest {


    /**
     * Test generating best move capturing piece while protecting against check
     */
    @Test
    public void generateBestMoveAvoidCheck() {

        String fenPosition = "8/2k5/2R2q2/8/2Q5/8/8/8";
        EngineBoard board = new EngineBoard(fenPosition);
        board.setTurnColor(Piece.Black);
        Engine e = new Engine();
        Move best = e.generateBestMove(board);
        System.out.println("Position:");
        System.out.println(board);
        System.out.println("Best move:");
        System.out.println(best);

        Move expected = new Move(21, 18, 0);

        assertEquals(expected, best);
    }


    /**
     * Test generating best move capturing piece while protecting against check,
     * although loosing with the next move.
     */
    @Test
    public void generateBestMoveAvoidCheckAlthoughLosing() {

        String fenPosition = "8/2k5/2R2q2/8/2Q5/2Q5/8/8";
        EngineBoard board = new EngineBoard(fenPosition);
        board.setTurnColor(Piece.Black);
        Engine e = new Engine();
        Move best = e.generateBestMove(board);
        System.out.println("Position:");
        System.out.println(board);
        System.out.println("Best move:");
        System.out.println(best);

        Move expected = new Move(21, 18, 0);

        assertEquals(expected, best);
    }
}