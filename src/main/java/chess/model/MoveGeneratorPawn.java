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
        int direction = board.getTurnColor() == Piece.Black ? MoveGenerator.DOWN : MoveGenerator.UP;

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
        int forwardPosition = startSquare + 2 * direction;
        int rank = Coordinate.fromIndex(startSquare)[1];
        if ((rank == 1 && direction == MoveGenerator.DOWN || rank == 6 && direction == MoveGenerator.UP)
                && board.getPieceAt(forwardPosition) == Piece.None) {
            generatedMoves.add(new Move(startSquare, forwardPosition, Move.PawnTwoForward));
        }
    }

    private static void captureDiagonal(Board board, List<Move> generatedMoves, int direction, int startSquare) {
        int opponentColor = board.getTurnColor() == Piece.Black ? Piece.White : Piece.Black;
        int diagonalPosition = startSquare+direction+MoveGenerator.LEFT;
        if (!Coordinate.isLeftMost(startSquare)) {
            if (Piece.isColor(board.getPieceAt(diagonalPosition), opponentColor))
                generatedMoves.add(new Move(startSquare, diagonalPosition));
            if (board.getEnPassantSquare() == diagonalPosition)
                generatedMoves.add(new Move(startSquare, diagonalPosition, Move.EnPassantCapture));
        }
        diagonalPosition = startSquare+direction+MoveGenerator.RIGHT;
        if (!Coordinate.isRightMost(startSquare)) {
            if (Piece.isColor(board.getPieceAt(diagonalPosition), opponentColor))
                generatedMoves.add(new Move(startSquare, diagonalPosition));
            if (board.getEnPassantSquare() == diagonalPosition)
                generatedMoves.add(new Move(startSquare, diagonalPosition, Move.EnPassantCapture));
        }
    }
}
