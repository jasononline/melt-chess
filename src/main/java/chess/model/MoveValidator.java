package chess.model;


import java.util.ArrayList;

/**
 * Checks if a move is legal
 */
public class MoveValidator {

    private static int findKing(Board board, int color) {
        ArrayList<Integer> positions = board.getPiecePositionsFor(color);
        for (int squarePosition : positions) {
            if (Piece.getType(board.getPieceAt(squarePosition)) == Piece.King)
                return squarePosition;
        }
        return -1;
    }

    /**
     * Checks for check
     * @param board the current position
     * @param color which piece color to check
     * @return number of pieces that attack the king
     */
    public static int checkCheck(Board board, int color) {
        int numChecks = 0;
        int opponentColor = (color == Piece.Black) ? Piece.White : Piece.Black;
        MoveGenerator generator = new MoveGenerator(board);
        int kingPosition = findKing(board, color);
        generator.swapColors();

        for (Move move : generator.generateMoves()) {
            if (move.getTargetSquare() == kingPosition)
                numChecks += 1;
        }
        return numChecks;
    }


    /**
     * Checks if a move is legal
     * @param board the current position
     * @param move what to check
     * @return true if the move is legal
     */
    public static boolean validateMove(Board board, Move move) {
        // check if move is any legal move found by the MoveGenerator
        MoveGenerator generator = new MoveGenerator(board);
        ArrayList<Move> generatedMoves = generator.generateMovesStartingAt(move.getStartSquare());
        if (!generatedMoves.contains(move))
            return false;

        // TODO check if king is in chess and stays in chess after the move

        // TODO check if move puts own king in chess

        // TODO if king is in check or any square between the king and the pawn is under attack he cannot castle
        return false;
    }
}
