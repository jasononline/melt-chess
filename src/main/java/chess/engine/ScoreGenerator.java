package chess.engine;

import chess.model.Piece;

import java.util.HashMap;
import java.util.Map;


/**
 * Assigns a score value to a given board position
 */
public class ScoreGenerator {



    // attacking these positions is beneficial in the early game
    private static final int[] centerSquares = new int[]{
            26, 27, 28, 29,
            34, 35, 36, 37,
    };


    /*A pawn is worth one point,
      a knight or bishop is worth three points,
      a rook is worth five points and a queen is worth nine points.
     */
    private static final Map<Integer, Integer> pieceValue = new HashMap<>(
            Map.of(Piece.Pawn, 100,
                    Piece.Knight, 300,
                    Piece.Bishop, 300,
                    Piece.Rook, 500,
                    Piece.Queen, 900 )
    );

    private EngineBoard board;
    // positive score value is good for white, negative good for black.
    private int score;

    /**
     * Return score for this position
     * @return computed score for this position
     */
    public int getScore() { return score; }


    /**
     * Initialize score generation for
     * @param board the current position
     */
    public ScoreGenerator(EngineBoard board) {
        this.board = board;
        scoreBoard();
    }


    /**
     * positive score value is good for white, negative good for black.
     */
    private void scoreBoard() {
        score = 0;
        score += scorePieceValue();
        board.setScore(score);
    }

    private int scorePieceValue() {
        int score = 0;
        int sign;
        for (int piece : board.getCapturedPieces()) {
            sign = Piece.isColor(piece, Piece.White) ? 1: -1;
            score += sign * pieceValue.get(Piece.getType(piece));
        }
        return score;
    }
}
