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
        System.out.println(board);
        List<Move> whiteMoves = Engine.getMoves(board, Piece.White);
        List<Move> blackMoves = Engine.getMoves(board, Piece.Black);

        ScoreGenerator scoreGenerator = new ScoreGenerator(board);
        scoreGenerator.scoreSquaresUnderAttack(whiteMoves, blackMoves);
        int s = Arrays.stream(scoreGenerator.squaresScore).sum();
        for (int row=0;row<8;row++) {
            for (int col=0;col<8;col++) {
                System.out.print(scoreGenerator.squaresScore[row*8+col] + "\t");
            }
            System.out.println();
        }
        assertEquals(0, s);
    }
}