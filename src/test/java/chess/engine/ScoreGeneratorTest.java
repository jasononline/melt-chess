package chess.engine;

import chess.model.Move;
import chess.model.Piece;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreGeneratorTest {

    String startposition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    /**
     * Test scoring which squares are under attack
     */
    @Test
    public void scoreSquaresUnderAttack() {

        EngineBoard board = new EngineBoard(startposition);
        Engine engine = new Engine();
        System.out.println(board);
        List<Move> whiteMoves = engine.getMoves(board, Piece.White);
        List<Move> blackMoves = engine.getMoves(board, Piece.Black);

        ScoreGenerator scoreGenerator = new ScoreGenerator(board);
        scoreGenerator.scoreSquaresUnderAttack(whiteMoves, blackMoves);
        int s = Arrays.stream(scoreGenerator.squaresScore).sum();
        assertEquals(0, s);
    }
}