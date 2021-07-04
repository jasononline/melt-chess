package chess.model;

import java.io.Serializable;

/**
 * Container for a possible Move. Contains start and destination square as well
 * as flags for capturing, castling and piece exchange
 */
public class Move implements Serializable {

	public static final int EnPassantCapture = 1;
	public static final int PawnTwoForward = 2;
	public static final int PromoteToKnight = Piece.Knight; // 3
	public static final int PromoteToBishop = Piece.Bishop; // 4
	public static final int PromoteToRook = Piece.Rook; // 5
	public static final int PromoteToQueen = Piece.Queen; // 6
	public static final int Castling = 7;

	private final int startSquare;
	private final int targetSquare;
	private int flag;

	/**
	 * Construct Move
	 * 
	 * @param startSquare  Integer representation starting square
	 * @param targetSquare Integer representation of the target square
	 */
	public Move(int startSquare, int targetSquare) {
		this.startSquare = startSquare;
		this.targetSquare = targetSquare;
		this.flag = 0;
	}

	/**
	 * Construct Move setting a flag
	 * 
	 * @param startSquare  Integer representation starting square
	 * @param targetSquare Integer representation of the target square
	 * @param flag         set any of the flags 0 to 7
	 */
	public Move(int startSquare, int targetSquare, int flag) {
		this(startSquare, targetSquare);
		this.flag = flag;
	}

	/**
	 * Parse the user input string and return Move object
	 *
	 * @param input User input like "e7-e8[Q]"
	 * @param game  the current game
	 * @return parsed move object from user input
	 */
	public static Move parseUserMoveInput(String input, Game game) {
		String[] squares = input.split("-"); // Split input by '-'
		int startSquare = Coordinate.toIndex(squares[0]); // start position in the board.squares array
		int targetSquare = Coordinate.toIndex(squares[1].substring(0, 2)); // target position in the board.squares array
		String flagString = squares[1].length() > 2 ? "" + squares[1].charAt(2) : "";
		int flag = 0;

		switch (flagString) {
			case "Q":
				flag = Move.PromoteToQueen;
				break;
			case "K":
				flag = Move.PromoteToKnight;
				break;
			case "R":
				flag = Move.PromoteToRook;
				break;
			case "B":
				flag = Move.PromoteToBishop;
				break;
			default:
				break;
		}

		Move move = new Move(startSquare, targetSquare, flag);
		game.addFlag(move);
		return move;
	}

	/**
	 * @return the starting square
	 */
	public int getStartSquare() {
		return startSquare;
	}

	/**
	 * @return the target square
	 */
	public int getTargetSquare() {
		return targetSquare;
	}

	/**
	 * @return returns the move flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * Set the flag field
	 * 
	 * @param flag the flag value
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * @return the string representation of the Move instance as used by the console
	 *         client
	 */
	@Override
	public String toString() {
		String promotion;
		switch (flag) {
			case Move.PromoteToQueen:
				promotion = "Q";
				break;
			case Move.PromoteToBishop:
				promotion = "B";
				break;
			case Move.PromoteToKnight:
				promotion = "N";
				break;
			case Move.PromoteToRook:
				promotion = "R";
				break;
			default:
				promotion = "";
		}
		return Coordinate.toString(startSquare) + "-" + Coordinate.toString(targetSquare) + promotion;
	}

	/**
	 * Checks whether this is the same as other move
	 * 
	 * @param other the other Move object
	 * @return true if objects have same fields else false
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof Move) {
			Move o = (Move) other;
			return startSquare == o.startSquare && targetSquare == o.targetSquare && flag == o.flag;
		} else {
			return super.equals(other);
		}
	}

	/**
	 * Calculates arbitrary hash code to accommodate pmd rules
	 * 
	 * @return sum of start-, targetSquare and the flag
	 */
	@Override
	public int hashCode() {
		return startSquare + targetSquare + flag;
	}
}
