package chess.engine;

import chess.model.Board;
import chess.model.Move;
import chess.model.MoveGenerator;
import chess.model.MoveValidator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Engine class for choosing next moves
 */
public class Engine {
    /**
     * Test strings
     *
     * hard ?
     * 1qkrr3/Rn2q3/PRPBqQ2/nqpbqB2/NQ2Nb2/1Q2Qq2/1Q2Qq2/1K2Qq2 w - -
     * 6r1/2rp1kpp/2qQp3/p3Pp1P/1pP2P2/1P2KP2/P5R1/6R1 w - -
     *
     * easy
     * 1r6/1kp1P2R/1pr2QK1/pNP2B2/B1nq4/bN2nr1p/5Bp1/1R1b4 w - -
     * qqqrq3/q7/b1NPQQBP/K1Q5/P1R5/b1BQRQNP/k7/nrqqn3 w - -
     *
     */


    // only promote to queen
    private static final List<Integer> promotionFilter = new ArrayList<>();
    private int maxDepth = 3; // only use odd values!

    private EngineBoard currentPosition;

    /**
     * Contructor of the engine
     */
    public Engine() {
        promotionFilter.add(Move.PromoteToBishop);
        promotionFilter.add(Move.PromoteToKnight);
        promotionFilter.add(Move.PromoteToRook);
    }


    /**
     * Returns a move, given a board position
     * @param board the current board position
     * @return the move the engine thinks is the best
     */
    public Move generateBestMove(Board board){
        int color = board.getTurnColor();

        List<EngineBoard> possiblePositions = getNextPositions(new EngineBoard(board));
        if (possiblePositions.isEmpty()) {
            return null;
        }

        EngineBoard bestPosition = null;
        int bestValue = Integer.MIN_VALUE;
        int value;

        for (EngineBoard position : possiblePositions) {
                value = alphaBetaPruning(position, maxDepth, color);
            if (bestValue < value) {
                bestValue = value;
                bestPosition = position;
            }
        }
        if (bestPosition == null) {
            return possiblePositions.get(0).getLastMove();
        }
        return bestPosition.getLastMove();
    }


    private int alphaBetaPruning(EngineBoard board, int depth, int maximizingColor) {
        int alpha, beta;
        alpha = Integer.MIN_VALUE;
        beta  = Integer.MAX_VALUE;
        currentPosition = board;
        return alphaBetaPruning(depth, maximizingColor, alpha, beta);
    }

    @SuppressWarnings({ "PMD.AvoidReassigningParameters", "PMD.UnusedLocalVariable" })
    // Since this is recursive method reassigning is needed
    // UnusedLocalVariable warning is wrong since value is used within the Math.max and Math.min Methods
    private int alphaBetaPruning(int depth, int maximizingColor, int alpha, int beta) {
        int value;
        if (depth == 0) {
            return currentPosition.getScore();
        }
        if (currentPosition.getTurnColor() == maximizingColor) {
            value = Integer.MIN_VALUE;
            for (EngineBoard newPosition : getNextPositions(currentPosition)) {
                value = Math.max(value, alphaBetaPruning(depth-1, maximizingColor, alpha, beta));
                if (value <= beta) {
                    break;
                }
                alpha = Math.max(alpha, value);
            }
            return value;
        }
        value = Integer.MAX_VALUE;
        for (EngineBoard newPosition : getNextPositions(currentPosition)) {
            value = Math.min(value, alphaBetaPruning(depth-1, maximizingColor, alpha, beta));
            if (value <= alpha) {
                break;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }


    /**
     * Gets all possible moves from the MoveGenerator,
     * filters them with the MoveValidator
     * and gives a score
     * @param board The position to branch from
     * @return List of scored positions
     */
    private List<EngineBoard> getNextPositions(EngineBoard board) {
        List<Move> moves = getMoves(board, board.getTurnColor());
        // make moves
        List<EngineBoard> positions = new LinkedList<>();
        for (Move move : moves) {
            EngineBoard newBoard = new EngineBoard(board.makeMove(move));
            positions.add(newBoard);
        }
        return positions;
    }


    /**
     * Compute list of all possible moves, but only promote to Queen
     * @param board the current position
     * @param color the color to move
     * @return a list of Move objects
     */
    protected static List<Move> getMoves(EngineBoard board, int color) {
        MoveGenerator generator = new MoveGenerator(board);
        if (generator.getTeamColor() != color)
            generator.swapColors();
        List<Move> moves = MoveValidator.filter(board, generator.generateMoves());
        // filter promotions other than queen
        moves.removeIf(m -> promotionFilter.contains(m.getFlag()));
        return moves;
    }
}
