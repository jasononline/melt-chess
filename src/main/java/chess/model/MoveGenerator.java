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
     * Generate list of possible pawn moves
     * @param startSquare the position of the pawn
     * @return ArrayList of Move objects
     */
    public ArrayList<Move> generatePawnMoves(int startSquare) {

        ArrayList<Move> generatedMoves = new ArrayList<>();
        int direction = teamColor == Piece.Black ? DOWN : UP;

        // move forward if possible
        int forwardPosition = startSquare + direction;
        if (!Coordinate.isUpMost(forwardPosition) && !Coordinate.isDownMost(forwardPosition)
                && board.getPieceAt(forwardPosition) == Piece.None) {
            generatedMoves.add(new Move(startSquare, forwardPosition));
        }

        // if moving forward lead to the last square in the file, exchanging the piece is possible
        if (board.getPieceAt(forwardPosition) == Piece.None
                && (Coordinate.isUpMost(forwardPosition) || Coordinate.isDownMost(forwardPosition))) {
            // Make the default queen exchange
            generatedMoves.add(new Move(startSquare, forwardPosition, Move.PromoteToQueen));
        }

        // if still in starting row, move two squares forward
        forwardPosition = startSquare + 2 * direction;
        int rank = Coordinate.fromIndex(startSquare)[1];
        if (rank == 1 && direction == DOWN || rank == 6 && direction == UP) {
            if (board.getPieceAt(forwardPosition) == Piece.None)
                generatedMoves.add(new Move(startSquare, forwardPosition, Move.PawnTwoForward));
        }

        // if possible, capture diagonal pieces
        for (int diagonalPosition : new int[]{startSquare+direction+LEFT, startSquare+direction+RIGHT}) {

            if (!Coordinate.isOnBorder(startSquare)) {
                if (Piece.isColor(board.getPieceAt(diagonalPosition), opponentColor))
                    generatedMoves.add(new Move(startSquare, diagonalPosition));
                if (board.getEnPassantSquare() == diagonalPosition)
                    generatedMoves.add(new Move(startSquare, diagonalPosition, Move.EnPassantCapture));
            }
        }

        return generatedMoves;
    }


    /**
     * Generates list of possible knight moves
     * @param startSquare the position of the knight
     * @return ArrayList of Move objects
     */
    public ArrayList<Move> generateKnightMoves(int startSquare) {
        ArrayList<Move>  generatedMoves = new ArrayList<>();
        ArrayList<Integer> directions = new ArrayList<>();
        int[] startCoordinates = Coordinate.fromIndex(startSquare);

        // make sure to not fall off the board
        if (1 < startCoordinates[1]) {
            if (0 < startCoordinates[0])
                directions.add(UP + UPLEFT);
            if (startCoordinates[0] < 7)
                directions.add(UP + UPRIGHT);
        }
        if (startCoordinates[1] < 6) {
            if (0 < startCoordinates[0])
                directions.add(DOWN + DOWNLEFT);
            if (startCoordinates[0] < 7)
                directions.add(DOWN + DOWNRIGHT);
        }
        if (1 < startCoordinates[0]) {
            if (0 < startCoordinates[1])
                directions.add(LEFT + UPLEFT);
            if (startCoordinates[1] < 7)
                directions.add(LEFT + DOWNLEFT);
        }
        if (startCoordinates[0] < 6) {
            if (0 < startCoordinates[1])
                directions.add(RIGHT + UPRIGHT);
            if (startCoordinates[1] < 7)
                directions.add(RIGHT + DOWNRIGHT);
        }

        // generate moves
        int targetSquare;
        for (int direction : directions) {
            targetSquare = startSquare + direction;
            if (Piece.isColor(board.getPieceAt(targetSquare), teamColor))
                continue;
            generatedMoves.add(new Move(startSquare, targetSquare));
        }
        return generatedMoves;
    }


    /**
     * Walks in each direction until border of the board or another piece is reached
     * @param startSquare the position of the piece
     * @param directions ArrayList of {UP, DOWNRIGHT, ...}
     * @return ArrayList of Move objects
     */
    private ArrayList<Move> generateDirectionalMoves(int startSquare, ArrayList<Integer> directions) {
        ArrayList<Move> generatedMoves = new ArrayList<>();
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


    private ArrayList<Integer> generateStartingDirectionsAcross(int startSquare) {
        ArrayList<Integer> directions = new ArrayList<>();
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


    private ArrayList<Integer> generateStartingDirectionsDiagonal(int startSquare) {
        ArrayList<Integer> directions = new ArrayList<>();
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
    public ArrayList<Move> generateAcrossMoves(int startSquare) {
        ArrayList<Integer> directions = generateStartingDirectionsAcross(startSquare);
        return generateDirectionalMoves(startSquare, directions);
    }


    /**
     * Generate diagonal moves
     * @param startSquare the position of the piece
     * @return ArrayList of Move objects
     */
    public ArrayList<Move> generateDiagonalMoves(int startSquare) {
        ArrayList<Integer> directions = generateStartingDirectionsDiagonal(startSquare);
        return generateDirectionalMoves(startSquare, directions);
    }


    /**
     * Generate moves for the rook
     * @param startSquare the position of the rook
     * @return ArrayList of Move objects
     */
    public ArrayList<Move> generateRookMoves(int startSquare) {
        return generateAcrossMoves(startSquare);
    }


    /**
     * Generate moves for the bishop
     * @param startSquare the position of the rook
     * @return ArrayList of Move objects
     */
    public ArrayList<Move> generateBishopMoves(int startSquare) {
        return generateDiagonalMoves(startSquare);
    }


    /**
     * Generate moves for the queen
     * @param startSquare the position of the queen
     * @return ArrayList of Move objects
     */
    public ArrayList<Move> generateQueenMoves(int startSquare) {
        ArrayList<Move> moves = generateDiagonalMoves(startSquare);
        moves.addAll(generateAcrossMoves(startSquare));
        return moves;
    }


    /**
     * Checks whether castling for current color is possible
     * @return array of two booleans {leftCastlingPossible, rightCastlingPossible}
     */
    private boolean[] isCastlingPossible(int startSquare) {
        // return if king not at initial position
        if (startSquare != 4 && startSquare != 60)
            return new boolean[]{false, false};
        boolean[] isPossible = new boolean[]{true, true};

        // check if no pieces to the left of king except the rook
        if (board.isCastlingA1Possible() && teamColor == Piece.White || board.isCastlingH1Possible() && teamColor == Piece.Black) {
            for (int i = 1; i < 4; i++) {
                if (Piece.getType(board.getPieceAt(startSquare - i)) != Piece.None) {
                    isPossible[0] = false;
                    break;
                }
            }
            if (Piece.getType(board.getPieceAt(startSquare - 4)) != Piece.Rook)
                isPossible[0] = false;
        } else {
            isPossible[0] = false;
        }
        // check if no pieces to the right of king except the rook
        if (board.isCastlingA8Possible() && teamColor == Piece.White || board.isCastlingH8Possible() && teamColor == Piece.Black) {
            for (int i = 1; i < 3; i++) {
                if (Piece.getType(board.getPieceAt(startSquare + i)) != Piece.None) {
                    isPossible[1] = false;
                    break;
                }
            }
            if (Piece.getType(board.getPieceAt(startSquare + 3)) != Piece.Rook)
                isPossible[1] = false;
        } else {
            isPossible[1] = false;
        }
        return isPossible;
    }


    /**
     * Generate moves for the king
     * @param startSquare the position of the king
     * @return ArrayList of Move objects
     */
    public ArrayList<Move> generateKingMoves(int startSquare) {
        // normal movement
        ArrayList<Move> generatedMoves = new ArrayList<>();
        ArrayList<Integer> directions = generateStartingDirectionsAcross(startSquare);
        directions.addAll(generateStartingDirectionsDiagonal(startSquare));

        int targetSquare, piece;
        for (int direction : directions) {
            targetSquare = startSquare + direction;
            piece = board.getPieceAt(targetSquare);
            if (Piece.getColor(piece) == teamColor)
                continue;
            generatedMoves.add(new Move(startSquare, targetSquare));
        }

        // castling
        boolean[] castlingPossible = isCastlingPossible(startSquare);
        if (castlingPossible[0]) {
            targetSquare = (teamColor == Piece.Black) ? 2 : 58;
            generatedMoves.add(new Move(startSquare, targetSquare, Move.Castling));
        }
        if (castlingPossible[1]) {
            targetSquare = (teamColor == Piece.Black) ? 6 : 62;
            generatedMoves.add(new Move(startSquare, targetSquare, Move.Castling));
        }
        return generatedMoves;
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
    public ArrayList<Move> generateMovesStartingAt(int startSquare) {
        switch (Piece.getType(board.getPieceAt(startSquare))) {
            case Piece.Pawn:
                return generatePawnMoves(startSquare);
            case Piece.Queen:
                return generateQueenMoves(startSquare);
            case Piece.Bishop:
                return generateBishopMoves(startSquare);
            case Piece.Rook:
                return generateRookMoves(startSquare);
            case Piece.Knight:
                return generateKnightMoves(startSquare);
            case Piece.King:
                return generateKingMoves(startSquare);
        }
        return new ArrayList<>();
    }

}
