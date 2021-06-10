package chess.engine;

import chess.model.Board;
import chess.model.Piece;

/**
 * Extension of the Board class for the engine
 */
public class EngineBoard extends Board implements Comparable<EngineBoard> {


    private int score = 0;

    EngineBoard(Board board) {
        super(board);
        new ScoreGenerator(this);
    }

    EngineBoard(String fen) {
        super(fen);
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * Implements Comparable
     * @param other Compare to this position
     * @return other - this
     */
    @Override
    public int compareTo(EngineBoard other) {
        return other.getScore() - score;
    }

    /**
     * @return A string containing the captured pieces
     */
    public String getCapturedPiecesToString() {
        StringBuilder result = new StringBuilder();
        for (int piece : getCapturedPieces()) {
            result.append(Piece.toString(piece));
        }
        return result.toString();
    }



}
