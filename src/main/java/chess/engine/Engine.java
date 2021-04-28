package chess.engine;

import chess.model.*;

import java.util.*;

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

    /*A pawn is worth one point,
      a knight or bishop is worth three points,
      a rook is worth five points and a queen is worth nine points.
     */
    private Map<Integer, Integer> pieceValue = new HashMap<>(
            Map.of(Piece.Pawn, 1,
                   Piece.Knight, 3,
                   Piece.Bishop, 3,
                   Piece.Rook, 5,
                   Piece.Queen, 9 )
    );

    private List<Integer> promotionFilter;

    /**
     * Contructor of the engine
     */
    public Engine() {
        promotionFilter = new ArrayList<>();
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
        return null;
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
        System.out.println("1: " + moveCounter);

        int sizeCheck;

        for (int i=0; i<max_depth-1; i++) {
            for (EngineBoard board : queue) {
                sizeCheck = nextQueue.size();
                nextQueue.addAll(getNextPositions(board));
                if (nextQueue.size() == sizeCheck)
                    break;
            }
            queue = nextQueue;
            moveCounter += queue.size();
            System.out.println("Level " + (i+2) + ": " + moveCounter +" nodes");
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
        MoveGenerator generator = new MoveGenerator(board);
        List<Move> moves;
        moves = MoveValidator.filter(board, generator.generateMoves());
        // filter promotions other than queen
        moves.removeIf(m -> promotionFilter.contains(m.getFlag()));
        // make moves
        List<EngineBoard> positions = new LinkedList<>();
        for (Move move : moves) {
            EngineBoard newBoard = new EngineBoard(board.makeMove(move));
            newBoard.setScore(scoreBoard(newBoard));
            positions.add(newBoard);
        }
        return positions;
    }

    /**
     * @param board Score this position
     * @return positive good for whate, negative good for black.
     */
    private int scoreBoard(EngineBoard board) {
        return scorePieceValue(board);
    }

    private int scorePieceValue(EngineBoard board) {
        int score = 0;
        int sign;
        for (int piece : board.getCapturedPieces()) {
            sign = Piece.isColor(piece, Piece.White) ? 1: -1;
            score += sign * pieceValue.get(Piece.getType(piece));
        }
        return score;
    }
}
