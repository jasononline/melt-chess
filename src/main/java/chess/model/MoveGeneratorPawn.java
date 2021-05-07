package chess.model;


import java.util.ArrayList;
import java.util.List;


/**
 * Implements rules for movement of the pawn piece
 */
public class MoveGeneratorPawn {

    /**
     * Generate list of possible pawn moves
     * @param startSquare the position of the pawn
     * @return ArrayList of Move objects
     */
    public static List<Move> generatePawnMoves(Board board, int startSquare) {
        List<Move> generatedMoves = new ArrayList<>();
        int direction = Piece.getColor(board.getPieceAt(startSquare)) == Piece.Black ? MoveGenerator.DOWN : MoveGenerator.UP;

        // move forward if possible
        moveForward(board, generatedMoves, direction, startSquare);

        // if still in starting row, move two squares forward
        moveTwoForward(board, generatedMoves, direction, startSquare);

        // if possible, capture diagonal pieces
        captureDiagonal(board, generatedMoves, direction, startSquare);
        return generatedMoves;
    }

    private static void moveForward(Board board, List<Move> generatedMoves, int direction, int startSquare) {
        int forwardPosition = startSquare + direction;
        if (!Coordinate.isUpMost(forwardPosition) && !Coordinate.isDownMost(forwardPosition)
                && board.getPieceAt(forwardPosition) == Piece.None) {
            generatedMoves.add(new Move(startSquare, forwardPosition));
        }

        // if moving forward lead to the last square in the file, promoting the piece is possible
        if (board.getPieceAt(forwardPosition) == Piece.None
                && (Coordinate.isUpMost(forwardPosition) || Coordinate.isDownMost(forwardPosition))) {
            generatedMoves.add(new Move(startSquare, forwardPosition, Move.PromoteToQueen));
            generatedMoves.add(new Move(startSquare, forwardPosition, Move.PromoteToKnight));
            generatedMoves.add(new Move(startSquare, forwardPosition, Move.PromoteToBishop));
            generatedMoves.add(new Move(startSquare, forwardPosition, Move.PromoteToRook));
        }
    }

    private static void moveTwoForward(Board board, List<Move> generatedMoves, int direction, int startSquare) {
        // don't jump over a piece
        int forwardPosition = startSquare + direction;
        if (board.getPieceAt(forwardPosition) != 0)
            return;
        forwardPosition += direction;
        int rank = Coordinate.fromIndex(startSquare)[1];
        if ((rank == 1 && direction == MoveGenerator.DOWN || rank == 6 && direction == MoveGenerator.UP)
                && board.getPieceAt(forwardPosition) == Piece.None) {
            generatedMoves.add(new Move(startSquare, forwardPosition, Move.PawnTwoForward));
        }
    }

    private static void captureDiagonal(Board board, List<Move> generatedMoves, int direction, int startSquare) {
        // TODO Refactor Left and Right diagonal capturing
        // check here if right or left most
        // create one capture diagonal method with left/right as parameter
        captureDiagonalLeft(board, generatedMoves, direction, startSquare);
        captureDiagonalRight(board, generatedMoves, direction, startSquare);
    }

    private static void captureDiagonalLeft(Board board, List<Move> generatedMoves, int direction, int startSquare) {
        int opponentColor = Piece.getColor(board.getPieceAt(startSquare)) == Piece.Black ? Piece.White : Piece.Black;
        int diagonalPosition = startSquare + direction + MoveGenerator.LEFT;

        if (!Coordinate.isLeftMost(startSquare)) {
            if (Piece.isColor(board.getPieceAt(diagonalPosition), opponentColor)) {
                if (Coordinate.isUpMost(diagonalPosition) || Coordinate.isDownMost(diagonalPosition)) {
                    addPromotionMoves(generatedMoves, startSquare, diagonalPosition);
                } else {
                    generatedMoves.add(new Move(startSquare, diagonalPosition));
                }
            }
            if (board.getEnPassantSquare() == diagonalPosition && Piece.isColor(board.getPieceAt(diagonalPosition - direction), opponentColor))
                generatedMoves.add(new Move(startSquare, diagonalPosition, Move.EnPassantCapture));
        }
    }

    private static void captureDiagonalRight(Board board, List<Move> generatedMoves, int direction, int startSquare) {
        int diagonalPosition = startSquare+direction+MoveGenerator.RIGHT;
        int opponentColor = Piece.getColor(board.getPieceAt(startSquare)) == Piece.Black ? Piece.White : Piece.Black;
        if (!Coordinate.isRightMost(startSquare)) {
            if (Piece.isColor(board.getPieceAt(diagonalPosition), opponentColor)) {
                if (Coordinate.isUpMost(diagonalPosition) || Coordinate.isDownMost(diagonalPosition)) {
                    addPromotionMoves(generatedMoves, startSquare, diagonalPosition);
                } else {
                    generatedMoves.add(new Move(startSquare, diagonalPosition));
                }
                if (board.getEnPassantSquare() == diagonalPosition)
                    generatedMoves.add(new Move(startSquare, diagonalPosition, Move.EnPassantCapture));
            }
        }
    }

    private static void addPromotionMoves(List<Move> generatedMoves, int startSquare, int targetSquare) {
            generatedMoves.add(new Move(startSquare, targetSquare, Move.PromoteToQueen));
            generatedMoves.add(new Move(startSquare, targetSquare, Move.PromoteToKnight));
            generatedMoves.add(new Move(startSquare, targetSquare, Move.PromoteToBishop));
            generatedMoves.add(new Move(startSquare, targetSquare, Move.PromoteToRook));
    }
}
