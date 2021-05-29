package chess.engine;

import chess.model.Coordinate;
import chess.model.Move;
import chess.model.Piece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    protected int[] squaresScore;
    private EngineBoard board;
    // positive score value is good for white, negative good for black.
    private int score;

    /**
     * @return computed score for this position
     */
    public int getScore() { return score; }


    public ScoreGenerator(EngineBoard board) {
        this.board = board;
        squaresScore = new int[64];
        scoreSquaresUnderAttack(
                Engine.getMoves(board, Piece.White),
                Engine.getMoves(board, Piece.Black)
        );
        scoreBoard();
    }



    /**
     * positive score value is good for white, negative good for black.
     */
    private void scoreBoard() {
        score = 0;
        score += scorePieceValue();
        score += scoreCenterAttack();
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


    protected void scoreSquaresUnderAttack(List<Move> whiteMoves, List<Move> blackMoves) {
        for (Move move : whiteMoves)
            scoreSquareUnderAttackFor(move);
        for (Move move : blackMoves)
            scoreSquareUnderAttackFor(move);
    }


    private void scoreSquareUnderAttackFor(Move move) {
        int piece = board.getPieceAt(move.getStartSquare());
        int targetSquare = move.getTargetSquare();
        int startSquare = move.getStartSquare();
        // check diagonal movement for pawn
        if (Piece.isType(piece, Piece.Pawn) && Coordinate.fromIndex(targetSquare)[0] == Coordinate.fromIndex(startSquare)[0]) {
            return;
        }
        int sign = Piece.isColor(piece, Piece.White) ? 1 : -1;
        squaresScore[targetSquare] += sign;
    }


    /**
     * Calculates a score based on how many center squares are under attack
     * @return score value
     */
    private int scoreCenterAttack() {
        int sum = 0;
        for (int square : centerSquares) {
            sum += squaresScore[square];
        }
        return sum;
    }
}
