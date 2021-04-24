package chess.model;

import java.util.ArrayList;

/**
 * Encapsulates the rules for moving pieces on the chess board
 * and offers functions for finding and validating moves.
 */
public class MoveGenerator {

    // constants for directions: newPosition = currentPosition + <direction>
    public static final int UP = -8;
    public static final int DOWN = 8;
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
     * Generates moves according to the rules for the pawn piece
     * @param startSquare the position of the pawn
     * @return ArrayList of Move objects
     */
    private ArrayList<Move> generatePawnMoves(int startSquare) {

        ArrayList<Move> generatedMoves = new ArrayList<>();
        int direction = teamColor == Piece.Black ? DOWN : UP;

        // move forward if possible
        int forwardPosition = startSquare + direction;
        if (8 <= forwardPosition && forwardPosition < 56 && board.getPieceAt(forwardPosition) == Piece.None) {
            generatedMoves.add(new Move(startSquare, forwardPosition));
        }

        // if moving forward lead to the last square, exchanging the piece is possible
        if (board.getPieceAt(forwardPosition) == Piece.None && (
                0 <= forwardPosition && forwardPosition < 8 ||
                56 <= forwardPosition && forwardPosition < 64)) {
            // Make the default queen exchange
            generatedMoves.add(new Move(startSquare, forwardPosition, Move.PromoteToQueen));
        }

        // if still in starting row, move two squares forward
        forwardPosition = startSquare + 2 * direction;
        if (8 <= startSquare && startSquare < 16 && direction == DOWN ||
                48 <= startSquare && startSquare < 56 && direction == UP) {
            if (board.getPieceAt(forwardPosition) == Piece.None)
                generatedMoves.add(new Move(startSquare, forwardPosition, Move.PawnTwoForward));
        }

        // if possible, capture diagonal pieces
        // TODO Check if diagonalPosition is a en passant capture square
        for (int diagonalPosition : new int[]{startSquare+direction+LEFT, startSquare+direction+RIGHT}) {
            if (Piece.isColor(board.getPieceAt(diagonalPosition), opponentColor))
                generatedMoves.add(new Move(startSquare, diagonalPosition));
        }

        return generatedMoves;
    }


    /**
     * Generate a list of all possible moves
     */
    public ArrayList<Move> generateMoves() {
        // TODO write tests (king, queen, rook, bishop, knight, pawn)
        ArrayList<Move> generatedMoves = new ArrayList<>();
        ArrayList<Integer> teamPositions = teamColor == Piece.Black ? blackPiecePositions : whitePiecePositions;

        for (int position : teamPositions) {
            generatedMoves.addAll(generateMovesFrom(position));
        }
        return generatedMoves;
    }


    /**
     * Returns true if the move is legal according to the rules of chess
     * @param move the move to check
     */
    public boolean isValid(Move move) {
        // TODO write tests
        // TODO write function
        return false;
    }


    /**
     * Generate a list of all possible moves starting on a given square
     * @param startSquare the starting square
     * @return ArrayList of Move objects
     */
    public ArrayList<Move> generateMovesFrom(int startSquare) {
        // gets tested via generateMoves()
        // TODO write function
        if (Piece.getType(board.getPieceAt(startSquare)) == Piece.Pawn)
            return generatePawnMoves(startSquare);
        return new ArrayList<>();
    }

}
