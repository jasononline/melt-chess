package chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements rules for movement of the king piece
 */
public class MoveGeneratorKing {


    private static boolean[] castlingWalker(Board board, int[] stops, boolean[] boardFlags, int startSquare) {
        boolean[] result = new boolean[]{true, true};
        int direction;
        for (int i=0;i<2;i++) {
            if (!boardFlags[i]) {
                result[i] = false;
                continue;
            }
            direction = 2*i-1;
            for (int step=1; step<stops[i]; step++) {
                if (Piece.getType(board.getPieceAt(startSquare + direction * step)) != Piece.None) {
                    result[i] = false;
                }
            }
        }
        return result;
    }


    private static boolean[] isBlackCastlingPossible(Board board, int startSquare) {
        int[] stops = new int[] {4, 3};
        boolean[] boardFlags = new boolean[]{
                board.isCastlingA8Possible(),
                board.isCastlingH8Possible()
        };
        return castlingWalker(board, stops, boardFlags, startSquare);
    }


    private static boolean[] isWhiteCastlingPossible(Board board, int startSquare) {
        int[] stops = new int[] {4, 3};
        boolean[] boardFlags = new boolean[]{
                board.isCastlingA1Possible(),
                board.isCastlingH1Possible()
        };
        return castlingWalker(board, stops, boardFlags, startSquare);
    }


    /**
     * Checks whether castling for current color is possible
     * @return array of two booleans {leftCastlingPossible, rightCastlingPossible}
     */
    private static boolean[] isCastlingPossible(Board board, int startSquare) {
        // return false if king not at initial position
        if (startSquare != 4 && startSquare != 60)
            return new boolean[]{false, false};
        if (startSquare < 8)
            return isBlackCastlingPossible(board, startSquare);
        return isWhiteCastlingPossible(board, startSquare);
    }


    /**
     * Generate moves for the king
     * @param startSquare the position of the king
     * @return ArrayList of Move objects
     */
    public static List<Move> generateKingMoves(Board board, int startSquare) {
        // normal movement
        List<Move> generatedMoves = new ArrayList<>();
        List<Integer> directions = MoveGeneratorDirectional.generateStartingDirectionsAcross(startSquare);
        directions.addAll(MoveGeneratorDirectional.generateStartingDirectionsDiagonal(startSquare));

        int targetSquare, piece;
        for (int direction : directions) {
            targetSquare = startSquare + direction;
            piece = board.getPieceAt(targetSquare);
            if (Piece.getColor(piece) == board.getTurnColor())
                continue;
            generatedMoves.add(new Move(startSquare, targetSquare));
        }

        // castling
        boolean[] castlingPossible = isCastlingPossible(board, startSquare);
        if (castlingPossible[0]) {
            targetSquare = (board.getTurnColor() == Piece.Black) ? 2 : 58;
            generatedMoves.add(new Move(startSquare, targetSquare, Move.Castling));
        }
        if (castlingPossible[1]) {
            targetSquare = (board.getTurnColor() == Piece.Black) ? 6 : 62;
            generatedMoves.add(new Move(startSquare, targetSquare, Move.Castling));
        }
        return generatedMoves;
    }
}
