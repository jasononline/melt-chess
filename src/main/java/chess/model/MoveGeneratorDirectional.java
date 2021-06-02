package chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates the simple directional moves for rook, bishop and queen
 */
public class MoveGeneratorDirectional {

	/**
	 * Generates moves across the current file and rank
	 * 
	 * @param board       current game board
	 * @param startSquare the position of the piece
	 * @return ArrayList of Move objects
	 */
	public static List<Move> generateAcrossMoves(Board board, int startSquare) {
		List<Integer> directions = generateStartingDirectionsAcross(startSquare);
		return generateDirectionalMoves(board, startSquare, directions);
	}

	/**
	 * Generate diagonal moves
	 * 
	 * @param board       current game board
	 * @param startSquare the position of the piece
	 * @return ArrayList of Move objects
	 */
	public static List<Move> generateDiagonalMoves(Board board, int startSquare) {
		List<Integer> directions = generateStartingDirectionsDiagonal(startSquare);
		return generateDirectionalMoves(board, startSquare, directions);
	}

	// Walks in each direction until border of the board or another piece is reached
	private static List<Move> generateDirectionalMoves(Board board, int startSquare, List<Integer> directions) {
		int teamColor = Piece.getColor(board.getPieceAt(startSquare));
		int opponentColor = (teamColor == Piece.White) ? Piece.Black : Piece.White;
		List<Move> generatedMoves = new ArrayList<>();
		int currentSquare;
		int piece;
		for (int direction : directions) {
			currentSquare = startSquare;
			do {
				currentSquare += direction;
				piece = board.getPieceAt(currentSquare);
				if (Piece.isType(piece, Piece.None) || Piece.isColor(piece, opponentColor)) {
					generatedMoves.add(new Move(startSquare, currentSquare));
					if (Piece.isColor(piece, opponentColor))
						break;
				}
				if (Piece.isColor(piece, teamColor))
					break;
			} while (!Coordinate.isOnBorderTowards(currentSquare, direction));
		}
		return generatedMoves;
	}

	/**
	 * Make sure to not walk off the board
	 * 
	 * @param startSquare position of the piece
	 * @return returns suitable directions
	 */
	public static List<Integer> generateStartingDirectionsAcross(int startSquare) {
		List<Integer> directions = new ArrayList<>();
		if (!Coordinate.isOnLeftBorder(startSquare))
			directions.add(MoveGenerator.LEFT);
		if (!Coordinate.isOnRightBorder(startSquare))
			directions.add(MoveGenerator.RIGHT);
		if (!Coordinate.isOnUpperBorder(startSquare))
			directions.add(MoveGenerator.UP);
		if (!Coordinate.isOnLowerBorder(startSquare))
			directions.add(MoveGenerator.DOWN);
		return directions;
	}

	/**
	 * Make sure to not walk off the board
	 * 
	 * @param startSquare position of the piece
	 * @return returns suitable directions
	 */
	public static List<Integer> generateStartingDirectionsDiagonal(int startSquare) {
		List<Integer> directions = new ArrayList<>();
		if (!Coordinate.isOnLeftBorder(startSquare) && !Coordinate.isOnUpperBorder(startSquare))
			directions.add(MoveGenerator.UPLEFT);
		if (!Coordinate.isOnRightBorder(startSquare) && !Coordinate.isOnUpperBorder(startSquare))
			directions.add(MoveGenerator.UPRIGHT);
		if (!Coordinate.isOnLeftBorder(startSquare) && !Coordinate.isOnLowerBorder(startSquare))
			directions.add(MoveGenerator.DOWNLEFT);
		if (!Coordinate.isOnRightBorder(startSquare) && !Coordinate.isOnLowerBorder(startSquare))
			directions.add(MoveGenerator.DOWNRIGHT);
		return directions;
	}
}
