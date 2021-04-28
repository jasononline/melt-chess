package chess.engine;

import chess.model.Piece;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the engine
 */
class EngineTest {

    /**
     * Test solving some positions
     */
    @Test
    void solve() {
        String problem1 = "1r6/1kp1P2R/1pr2QK1/pNP2B2/B1nq4/bN2nr1p/5Bp1/1R1b4 w - -";
        EngineBoard board1 = new EngineBoard(problem1);
        Engine engine = new Engine();
        System.out.println("Running test position 1:");
        System.out.println(board1);
        assertEquals(Piece.White, engine.solve(board1));
    }
}