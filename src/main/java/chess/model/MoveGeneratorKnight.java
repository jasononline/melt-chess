package chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implement rules for movement of the knight piece
 */
public class MoveGeneratorKnight {

    // make sure to not fall off the board
    private static List<Integer> generateKnightDirectionsLeftRight(int startSquare) {
        int[] startCoordinates = Coordinate.fromIndex(startSquare);
        List<Integer> directions = new ArrayList<>();
        if (1 < startCoordinates[0]) {
            if (0 < startCoordinates[1])
                directions.add(MoveGenerator.LEFT + MoveGenerator.UPLEFT);
            if (startCoordinates[1] < 7)
                directions.add(MoveGenerator.LEFT + MoveGenerator.DOWNLEFT);
        }
        if (startCoordinates[0] < 6) {
            if (0 < startCoordinates[1])
                directions.add(MoveGenerator.RIGHT + MoveGenerator.UPRIGHT);
            if (startCoordinates[1] < 7)
                directions.add(MoveGenerator.RIGHT + MoveGenerator.DOWNRIGHT);
        }
        return directions;
    }

    private static List<Integer> generateKnightDirectionsUpDown(int startSquare) {
        int[] startCoordinates = Coordinate.fromIndex(startSquare);
        List<Integer> directions = new ArrayList<>();
        if (1 < startCoordinates[1]) {
            if (0 < startCoordinates[0])
                directions.add(MoveGenerator.UP + MoveGenerator.UPLEFT);
            if (startCoordinates[0] < 7)
                directions.add(MoveGenerator.UP + MoveGenerator.UPRIGHT);
        }
        if (startCoordinates[1] < 6) {
            if (0 < startCoordinates[0])
                directions.add(MoveGenerator.DOWN + MoveGenerator.DOWNLEFT);
            if (startCoordinates[0] < 7)
                directions.add(MoveGenerator.DOWN + MoveGenerator.DOWNRIGHT);
        }
        return directions;
    }


    /**
     * Generates list of possible knight moves
     * @param startSquare the position of the knight
     * @return ArrayList of Move objects
     */
    public static List<Move> generateKnightMoves(Board board, int startSquare, int teamColor) {
        List<Move>  generatedMoves = new ArrayList<>();
        List<Integer> directions = generateKnightDirectionsLeftRight(startSquare);
        directions.addAll(generateKnightDirectionsUpDown(startSquare));

        int targetSquare;
        for (int direction : directions) {
            targetSquare = startSquare + direction;
            if (Piece.isColor(board.getPieceAt(targetSquare), teamColor))
                continue;
            generatedMoves.add(new Move(startSquare, targetSquare));
        }
        return generatedMoves;
    }

}
