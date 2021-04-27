package chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the rules for moving pieces on the chess board.
 */
public class MoveGenerator {

    // constants for directions: newPosition = currentPosition + <direction> + <direction> + ...
    public static final int UP = -8;
    public static final int DOWN = 8;
    public static final int LEFT = -1;
    public static final int RIGHT = 1;
    public static final int UPLEFT = -9;
    public static final int UPRIGHT = -7;
    public static final int DOWNLEFT = 7;
    public static final int DOWNRIGHT = 9;

    private Board board;
    private List<Integer> whitePiecePositions;
    private List<Integer> blackPiecePositions;

    private int teamColor, opponentColor;

    MoveGenerator(Board board) {
        this.board = board;
        whitePiecePositions = board.getPiecePositionsFor(Piece.White);
        blackPiecePositions = board.getPiecePositionsFor(Piece.Black);
        teamColor = board.getTurnColor();
        opponentColor = teamColor == Piece.White ? Piece.Black : Piece.White;
    }


    /**
     * swap the current turn color
     * @return the new teamColor
     */
    public int swapColors() {
        int tmp = teamColor;
        teamColor = opponentColor;
        opponentColor = tmp;
        return teamColor;
    }


    /**
     * @return the current turn teamColor
     */
    public int getTeamColor() {
        return teamColor;
    }


    /**
     * Generates list of possible knight moves
     * @param startSquare the position of the knight
     * @return ArrayList of Move objects
     */
    public List<Move> generateKnightMoves(int startSquare) {
        return MoveGeneratorKnight.generateKnightMoves(board, startSquare, teamColor);
    }


    /**
     * Walks in each direction until border of the board or another piece is reached
     * @param startSquare the position of the piece
     * @param directions ArrayList of {UP, DOWNRIGHT, ...}
     * @return ArrayList of Move objects
     */
    private List<Move> generateDirectionalMoves(int startSquare, List<Integer> directions) {
        List<Move> generatedMoves = new ArrayList<>();
        int currentSquare;
        int piece;
        for (int direction : directions) {
            currentSquare = startSquare;
            do {
                currentSquare += direction;
                piece = board.getPieceAt(currentSquare);
                if (Piece.getType(piece) == Piece.None || Piece.isColor(piece, opponentColor)) {
                    generatedMoves.add(new Move(startSquare, currentSquare));
                    if (Piece.isColor(piece, opponentColor))
                        break;
                }
                if (Piece.isColor(piece, teamColor))
                    break;
            } while (!Coordinate.isOnBorder(currentSquare));
        }
        return generatedMoves;
    }


    private List<Integer> generateStartingDirectionsAcross(int startSquare) {
        List<Integer> directions = new ArrayList<>();
        // do not walk off the board
        if (!Coordinate.isLeftMost(startSquare))
            directions.add(LEFT);
        if (!Coordinate.isRightMost(startSquare))
            directions.add(RIGHT);
        if (!Coordinate.isUpMost(startSquare))
            directions.add(UP);
        if (!Coordinate.isDownMost(startSquare))
            directions.add(DOWN);
        return directions;
    }


    private List<Integer> generateStartingDirectionsDiagonal(int startSquare) {
        List<Integer> directions = new ArrayList<>();
        // do not walk off the board
        if (!Coordinate.isLeftMost(startSquare) && !Coordinate.isUpMost(startSquare))
            directions.add(UPLEFT);
        if (!Coordinate.isRightMost(startSquare) && !Coordinate.isUpMost(startSquare))
            directions.add(UPRIGHT);
        if (!Coordinate.isLeftMost(startSquare) && !Coordinate.isDownMost(startSquare))
            directions.add(DOWNLEFT);
        if (!Coordinate.isRightMost(startSquare) && !Coordinate.isDownMost(startSquare))
            directions.add(DOWNRIGHT);
        return directions;
    }


    /**
     * Generates moves across the current file and rank
     * @param startSquare the position of the piece
     * @return ArrayList of Move objects
     */
    public List<Move> generateAcrossMoves(int startSquare) {
        List<Integer> directions = generateStartingDirectionsAcross(startSquare);
        return generateDirectionalMoves(startSquare, directions);
    }


    /**
     * Generate diagonal moves
     * @param startSquare the position of the piece
     * @return ArrayList of Move objects
     */
    public List<Move> generateDiagonalMoves(int startSquare) {
        List<Integer> directions = generateStartingDirectionsDiagonal(startSquare);
        return generateDirectionalMoves(startSquare, directions);
    }


    /**
     * Generate moves for the rook
     * @param startSquare the position of the rook
     * @return ArrayList of Move objects
     */
    public List<Move> generateRookMoves(int startSquare) {
        return generateAcrossMoves(startSquare);
    }


    /**
     * Generate moves for the bishop
     * @param startSquare the position of the rook
     * @return ArrayList of Move objects
     */
    public List<Move> generateBishopMoves(int startSquare) {
        return generateDiagonalMoves(startSquare);
    }


    /**
     * Generate moves for the queen
     * @param startSquare the position of the queen
     * @return ArrayList of Move objects
     */
    public List<Move> generateQueenMoves(int startSquare) {
        List<Move> moves = generateDiagonalMoves(startSquare);
        moves.addAll(generateAcrossMoves(startSquare));
        return moves;
    }


    /**
     * Generate moves for the king
     * @param startSquare the position of the king
     * @return ArrayList of Move objects
     */
    public List<Move> generateKingMoves(int startSquare) {
        return MoveGeneratorKing.generateKingMoves(board, startSquare);
    }


    /**
     * Generate possible moves for current color
     * @return ArrayList of Move objects
     */
    public List<Move> generateMoves() {
        List<Move> generatedMoves = new ArrayList<>();
        List<Integer> teamPositions = teamColor == Piece.Black ? blackPiecePositions : whitePiecePositions;

        for (int position : teamPositions) {
            generatedMoves.addAll(generateMovesStartingAt(position));
        }
        return generatedMoves;
    }


    /**
     * Generate a list of all possible moves for any piece standing on a given square
     * @param startSquare the starting square
     * @return ArrayList of Move objects
     */
    public List<Move> generateMovesStartingAt(int startSquare) {
        switch (Piece.getType(board.getPieceAt(startSquare))) {
            case Piece.Pawn:
                return MoveGeneratorPawn.generatePawnMoves(board, startSquare);
            case Piece.Queen:
                return generateQueenMoves(startSquare);
            case Piece.Bishop:
                return generateBishopMoves(startSquare);
            case Piece.Rook:
                return generateRookMoves(startSquare);
            case Piece.Knight:
                return generateKnightMoves(startSquare);
            case Piece.King:
                return MoveGeneratorKing.generateKingMoves(board, startSquare);
        }
        return new ArrayList<>();
    }
}
