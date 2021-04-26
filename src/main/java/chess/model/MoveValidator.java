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
     * @param teamColor which piece color to check
     * @return number of pieces that attack the king
     */
    public static int checkCheck(Board board, int teamColor) {
        int numChecks = 0;
        int opponentColor = (teamColor == Piece.Black) ? Piece.White : Piece.Black;
        MoveGenerator generator = new MoveGenerator(board);
        int kingPosition = findKing(board, teamColor);

        // swap color to opponent
        if (generator.getTeamColor() == teamColor)
            generator.swapColors();

        // find opponent moves and count if king is in check
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

        Board boardAfterMove = board.makeMove(move);

        // check if king is in check and stays in check after the move
        int inCheck = checkCheck(board, board.getTurnColor());
        if (0 < inCheck) {
            if (0 < checkCheck(boardAfterMove, board.getTurnColor()))
                return false;
        }

        // check if move puts own king in chess
        if (0 < checkCheck(boardAfterMove, board.getTurnColor()))
            return false;

        // if king is in check or any square between the king and the pawn is under attack he cannot castle
        if (move.getFlag() == Move.Castling) {
            if (0 < inCheck)
                return false;
            generator.swapColors();
            ArrayList<Move> opponentMoves = generator.generateMoves();
            // check left side
            if (move.getTargetSquare() < move.getStartSquare()) {
                for (int i=1;i<4;i++) {
                    if (isTarget(move.getStartSquare() - i, opponentMoves))
                        return false;
                }
            } else {
                // check right side
                for (int i=1;i<3;i++) {
                    if (isTarget(move.getStartSquare() + i, opponentMoves))
                        return false;
                }
            }
        }
        return true;
    }


    /**
     * Checks if squarePosition is a targetSquare of any move in moves
     * @param squarePosition what position to check
     * @param moves ArrayList of moves to check
     * @return true if any move contains squarePosition as target
     */
    private static boolean isTarget(int squarePosition, ArrayList<Move> moves) {
        for (Move m : moves) {
            if (m.getTargetSquare() == squarePosition)
                return true;
        }
        return false;
    }
}