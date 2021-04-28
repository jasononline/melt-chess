package chess.model;

import java.util.List;
import java.util.Stack;

/**
 * Represents a game of chess
 */
public class Game {

    private static final String startingPositionFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    public static final int CHECKMATE = 1;
    public static final int REMIS = 2;

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
    public boolean attemptMove(Move move) {
        if (!MoveValidator.validateMove(history.peek(), move))
            return false;
        history.push(history.peek().makeMove(move));
        return true;
    }


    /**
     * Pop the last board of the history and set it as current position
     * @return The previous board position or a empty board if history is empty
     */
    public Board undoMove() {
        if (history.isEmpty())
            return new Board();
        history.pop();
        return history.peek();
    }

    /**
     * Returns Checkmate or Remis for current player
     * @param board current position
     * @return 0: NONE, 1: CHECKMATE, 2: REMIS
     */
    public int checkWinCondition(Board board) {
        MoveGenerator generator = new MoveGenerator(board);
        int check = MoveValidator.checkCheck(board, board.getTurnColor());
        List<Move> possibleMoves = MoveValidator.filter(board, generator.generateMoves());
        if (possibleMoves.isEmpty()) {
            if (check != 0)
                return CHECKMATE;
            return REMIS;
        }
        return 0;
    }
}
