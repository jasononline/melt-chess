package chess.engine;

import chess.model.*;

import java.util.ArrayList;
import java.util.Collections;
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
        List<EngineBoard> possibleMoves = getNextPositions(new EngineBoard(board));
        if (possibleMoves.isEmpty())
            return null;
        Collections.sort(possibleMoves);
        if (board.getTurnColor() == Piece.White)
            return possibleMoves.get(possibleMoves.size() - 1).getLastMove();
        return possibleMoves.get(0).getLastMove();
    }


    /**
     * Attempts to solve the position
     * @param startPosition initial position of the problem
     * @return Piece.White or Piece.Black
     */
    public int solve(EngineBoard startPosition) {
        int max_depth = 4;
        List<EngineBoard> queue = getNextPositions(startPosition);
        List<EngineBoard> nextQueue = new LinkedList<>();

        int moveCounter = queue.size();
        System.out.println("Level 1: " + moveCounter);

        int sizeCheck;

        for (int i=1; i<max_depth; i++) {
            for (EngineBoard board : queue) {
                sizeCheck = nextQueue.size();
                nextQueue.addAll(getNextPositions(board));
                if (nextQueue.size() == sizeCheck)
                    break;
            }
            queue = nextQueue;
            moveCounter += queue.size();
            System.out.println("Level " + (i+1) + ": " + moveCounter +" nodes");
            nextQueue = new LinkedList<>();
            Collections.sort(queue);
            System.out.println("Best Position of run :");
            System.out.println(queue.get(0).getCapturedPiecesToString() + " (" + queue.get(0).getScore() +")");
            System.out.println(queue.get(0));
        }
        return moveCounter;
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
            ScoreGenerator scoreGen = new ScoreGenerator(newBoard);
            positions.add(newBoard);
        }
        return positions;
    }


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
