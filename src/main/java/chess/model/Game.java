package chess.model;

import java.util.Stack;

/**
 * Represents a game of chess
 */
public class Game {

    private static final String startingPositionFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";

    private Stack<Board> history;


    /**
     * @return returns the current position
     */
    public Board getCurrentPosition() { return history.peek(); }


    /**
     * Start a new game.
     */
    public Game() {
        history = new Stack<>();
        history.push(new Board(startingPositionFEN));
    }


    /**
     * Attempt to make a move.
     * @return true if the move was legal and was applied successfully
     */
    public boolean attemptMovePiece(Move move) {
        if (!MoveValidator.validateMove(history.peek(), move))
            return false;
        history.push(history.peek().makeMove(move));
        return true;
    }


    /**
     * Pop the last board of the history and set it as current position
     * @return The previous board position
     */
    public Board undoMove() {
        history.pop();
        return history.peek();
    }
}
