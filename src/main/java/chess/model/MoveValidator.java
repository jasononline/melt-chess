package chess.model;


import java.util.List;

/**
 * Checks if a move is legal
 */
public class MoveValidator {

    private static int findKing(Board board, int color) {
        List<Integer> positions = board.getPiecePositionsFor(color);
        for (int squarePosition : positions) {
            if (Piece.getType(board.getPieceAt(squarePosition)) == Piece.King)
                return squarePosition;
        }
        return -1;
    }


    /**
     * Filters a list of moves
     * @param moves a list of moves
     * @return a list of valid moves
     */
    public static List<Move> filter(Board board, List<Move> moves) {
        moves.removeIf(m -> !validateMove(board, m));
        return moves;
    }


    /**
     * Checks for check
     * @param board the current position
     * @param teamColor which piece color to check
     * @return 0: no check, 1: check, 2: double check
     */
    public static int checkCheck(Board board, int teamColor) {
        int numChecks = 0;
        MoveGenerator generator = new MoveGenerator(board);
        int kingPosition = findKing(board, teamColor);

        // swap color to opponent
        if (generator.getTeamColor() == teamColor)
            generator.swapColors();

        // find opponent moves and count if king is in check
        for (Move move : generator.generateMoves()) {
            if (move.getTargetSquare() == kingPosition)
                numChecks += 1;
            if (numChecks == 2)
                return numChecks;
        }
        return numChecks;
    }


    // check if move is any legal move found by the MoveGenerator
    private static boolean isLegalMove(MoveGenerator generator, Move move) {
        return generator.generateMovesStartingAt(move.getStartSquare()).contains(move);
    }


    // if king is in check or any square between the king and the pawn is under attack he cannot castle
    private static boolean canCastle(MoveGenerator generator, Move move, int inCheck) {
            if (0 < inCheck)
                return false;
            generator.swapColors();
            List<Move> opponentMoves = generator.generateMoves();
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
        return true;
    }


    /**
     * Checks if a move is legal
     * @param board the current position
     * @param move what to check
     * @return true if the move is legal
     */
    public static boolean validateMove(Board board, Move move) {
        MoveGenerator generator = new MoveGenerator(board);
        if (!isLegalMove(generator, move))
            return false;

        Board boardAfterMove = board.makeMove(move);

        int inCheck = checkCheck(board, board.getTurnColor());
        /*
        // check if king is in check and stays in check after the move

        if (0 < inCheck && 0 < checkCheck(boardAfterMove, board.getTurnColor())) {
            return false;
        }
        */
        // check if move puts own king in chess
        if (0 < checkCheck(boardAfterMove, board.getTurnColor()))
            return false;

        return move.getFlag() != Move.Castling || canCastle(generator, move, inCheck);
    }


    /**
     * Checks if squarePosition is a targetSquare of any move in moves
     * @param squarePosition what position to check
     * @param moves ArrayList of moves to check
     * @return true if any move contains squarePosition as target
     */
    private static boolean isTarget(int squarePosition, List<Move> moves) {
        for (Move m : moves) {
            if (m.getTargetSquare() == squarePosition)
                return true;
        }
        return false;
    }
}
