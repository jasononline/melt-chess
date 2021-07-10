package chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements rules for movement of the pawn piece
 */
public class MoveGeneratorPawn {

	/**
	 * Generate list of possible pawn moves
	 * 
	 * @param board       current game board
	 * @param startSquare the position of the pawn
	 * @return ArrayList of Move objects
	 */
	public static List<Move> generatePawnMoves(Board board, int startSquare) {
		List<Move> generatedMoves = new ArrayList<>();
		int direction = Piece.getColor(board.getPieceAt(startSquare)) == Piece.Black ? MoveGenerator.DOWN
				: MoveGenerator.UP;

		// move forward if possible
		moveForward(board, generatedMoves, direction, startSquare);

		// if still in starting row, move two squares forward
		moveTwoForward(board, generatedMoves, direction, startSquare);

		// if possible, capture diagonal pieces
		captureDiagonal(board, generatedMoves, direction, startSquare);
		return generatedMoves;
	}

	// Move pawn one square forward
	private static void moveForward(Board board, List<Move> generatedMoves, int direction, int startSquare) {
		int forwardPosition = startSquare + direction;
		if (!Coordinate.isOnUpperBorder(forwardPosition) && !Coordinate.isOnLowerBorder(forwardPosition)
				&& board.getPieceAt(forwardPosition) == Piece.None) {
			generatedMoves.add(new Move(startSquare, forwardPosition));
		}

		// if moving forward lead to the last square in the file, promoting the piece is
		// possible
		if (board.getPieceAt(forwardPosition) == Piece.None
				&& (Coordinate.isOnUpperBorder(forwardPosition) || Coordinate.isOnLowerBorder(forwardPosition))) {
			addPromotionMoves(generatedMoves, startSquare, forwardPosition);
		}
	}

	// Move pawn two squares forward
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

	// perform diagonal capturing
	private static void captureDiagonal(Board board, List<Move> generatedMoves, int direction, int startSquare) {
		int diagonalPosition;
		int[] positionalValues = new int[] {startSquare, direction, 0};
		if (!Coordinate.isOnLeftBorder(startSquare)) {
			diagonalPosition = startSquare + direction + MoveGenerator.LEFT;
			positionalValues[2] = diagonalPosition;
			captureDiagonalToward(board, generatedMoves, positionalValues);
		}
		if (!Coordinate.isOnRightBorder(startSquare)) {
			diagonalPosition = startSquare + direction + MoveGenerator.RIGHT;
			positionalValues[2] = diagonalPosition;
			captureDiagonalToward(board, generatedMoves, positionalValues);
		}
	}

	// capture either diagonal right or left
	private static void captureDiagonalToward(Board board, List<Move> generatedMoves, int[] positionalValues) {
		int startSquare = positionalValues[0];
		int direction = positionalValues[1];
		int diagonalPosition = positionalValues[2];
		int opponentColor = Piece.getColor(board.getPieceAt(startSquare)) == Piece.Black ? Piece.White : Piece.Black;

		if (Piece.isColor(board.getPieceAt(diagonalPosition), opponentColor)) {
			if (Coordinate.isOnUpperBorder(diagonalPosition) || Coordinate.isOnLowerBorder(diagonalPosition)) {
				addPromotionMoves(generatedMoves, startSquare, diagonalPosition);
			} else {
				generatedMoves.add(new Move(startSquare, diagonalPosition));
			}
		}
		if (board.getEnPassantSquare() == diagonalPosition
				&& Piece.isColor(board.getPieceAt(diagonalPosition - direction), opponentColor))
			generatedMoves.add(new Move(startSquare, diagonalPosition, Move.EnPassantCapture));
	}

	private static void addPromotionMoves(List<Move> generatedMoves, int startSquare, int targetSquare) {
		generatedMoves.add(new Move(startSquare, targetSquare, Move.PromoteToQueen));
		generatedMoves.add(new Move(startSquare, targetSquare, Move.PromoteToKnight));
		generatedMoves.add(new Move(startSquare, targetSquare, Move.PromoteToBishop));
		generatedMoves.add(new Move(startSquare, targetSquare, Move.PromoteToRook));
	}
}
