package chess.model;

import java.util.ArrayList;

/**
 * Encapsulates the rules for moving pieces on the chess board
 * and offers functions for finding and validating moves.
 */
public class MoveGenerator {

    // constants for directions: newPosition = currentPosition + <direction>
    public static final int UP = -8;
    public static final int DOWN = -8;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    public static final int UPLEFT = -7;
    public static final int UPRIGHT = -9;
    public static final int DOWNLEFT = 7;
    public static final int DOWNRIGHT = 9;

    private Board board;
    private ArrayList<Integer> whitePiecePositions;
    private ArrayList<Integer> blackPiecePositions;
    private int teamColor, opponentColor;

    MoveGenerator(Board board) {
        this.board = board;
        whitePiecePositions = board.getPiecePositionsFor(Piece.White);
        blackPiecePositions = board.getPiecePositionsFor(Piece.Black);
        teamColor = board.getTurnColor();
        opponentColor = teamColor == Piece.White ? Piece.Black : Piece.White;
    }


    /**
     * Generate a list of all possible moves
     * @param board the current state of the game
     */
    public static ArrayList<Move> generateMoves(Board board) {
        // TODO write tests
        // TODO write function
        return null;
    }


    /**
     * Generate a list of all possible moves for pieces of a given color
     * @param board the current state of the game
     * @param color pieces color to find moves for
     */
    public static ArrayList<Move> generateMovesFor(Board board, int color) {
        // TODO write tests
        // TODO write function
        return null;
    }


    /**
     * Generate a list of all possible moves for pieces of the current color
     * @param board the current state of the game
     */
    public static ArrayList<Move> generateNextMoves(Board board) {
        // TODO write tests
        // TODO write function
        return null;
    }


    /**
     * Returns true if the move is legal according to the rules of chess
     * @param board the current state of the game
     * @param move the move to check
     */
    public static ArrayList<Move> isValid(Board board, Move move) {
        // TODO write tests
        // TODO write function
        return null;
    }


    /**
     * Generate a list of all possible moves starting on a given square
     * @param board the current state of the game
     * @param startSquare the starting square
     */
    public static ArrayList<Move> generateMovesFrom(Board board, int startSquare) {
        // TODO write tests
        // TODO write function
        return null;
    }

}
