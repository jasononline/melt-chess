package chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the rules for moving pieces on the chess board.
 */
public class MoveGenerator {

	// constants for directions: newPosition = currentPosition + <direction> +
	// <direction> + ...
	public static final int UP = -8;
	public static final int DOWN = 8;
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int UPLEFT = -9;
	public static final int UPRIGHT = -7;
	public static final int DOWNLEFT = 7;
	public static final int DOWNRIGHT = 9;

	private final Board board;
	private final List<Integer> whitePiecePositions;
	private final List<Integer> blackPiecePositions;

	private int teamColor, opponentColor;

	/**
	 * Create new MoveGeneraton instance of
	 * 
	 * @param board the current position
	 */
	public MoveGenerator(Board board) {
		this.board = board;
		whitePiecePositions = board.getPiecePositionsFor(Piece.White);
		blackPiecePositions = board.getPiecePositionsFor(Piece.Black);
		teamColor = board.getTurnColor();
		opponentColor = teamColor == Piece.White ? Piece.Black : Piece.White;
	}

	/**
	 * swap the current turn color
	 * 
	 * @return the new teamColor
	 */
	public int swapColors() {
		int tmp = teamColor;
		teamColor = opponentColor;
		opponentColor = tmp;
		return teamColor;
	}

	/**
	 * @return the current turn teamColor
	 */
	public int getTeamColor() {
		return teamColor;
	}

	/**
	 * Generates list of possible knight moves
	 * 
	 * @param startSquare the position of the knight
	 * @return ArrayList of Move objects
	 */
	public List<Move> generateKnightMoves(int startSquare) {
		return MoveGeneratorKnight.generateKnightMoves(board, startSquare, teamColor);
	}

	/**
	 * Generate moves for the rook
	 * 
	 * @param startSquare the position of the rook
	 * @return ArrayList of Move objects
	 */
	public List<Move> generateRookMoves(int startSquare) {
		return MoveGeneratorDirectional.generateAcrossMoves(board, startSquare);
	}

	/**
	 * Generate moves for the bishop
	 * 
	 * @param startSquare the position of the rook
	 * @return ArrayList of Move objects
	 */
	public List<Move> generateBishopMoves(int startSquare) {
		return MoveGeneratorDirectional.generateDiagonalMoves(board, startSquare);
	}

	/**
	 * Generate moves for the queen
	 * 
	 * @param startSquare the position of the queen
	 * @return ArrayList of Move objects
	 */
	public List<Move> generateQueenMoves(int startSquare) {
		List<Move> moves = MoveGeneratorDirectional.generateDiagonalMoves(board, startSquare);
		moves.addAll(MoveGeneratorDirectional.generateAcrossMoves(board, startSquare));
		return moves;
	}

	/**
	 * Generate moves for the king
	 * 
	 * @param startSquare the position of the king
	 * @return ArrayList of Move objects
	 */
	public List<Move> generateKingMoves(int startSquare) {
		return MoveGeneratorKing.generateKingMoves(board, startSquare);
	}

	/**
	 * Generate possible moves for current color
	 * 
	 * @return ArrayList of Move objects
	 */
	public List<Move> generateMoves() {
		List<Move> generatedMoves = new ArrayList<>();
		List<Integer> teamPositions = teamColor == Piece.Black ? blackPiecePositions : whitePiecePositions;

		for (int position : teamPositions) {
			generatedMoves.addAll(generateMovesStartingAt(position));
		}
		return generatedMoves;
	}

	/**
	 * Generate a list of all possible moves for any piece standing on a given
	 * square
	 * 
	 * @param startSquare the starting square
	 * @return ArrayList of Move objects
	 */
	public List<Move> generateMovesStartingAt(int startSquare) {
		switch (Piece.getType(board.getPieceAt(startSquare))) {
			case Piece.Pawn:
				return MoveGeneratorPawn.generatePawnMoves(board, startSquare);
			case Piece.Queen:
				return generateQueenMoves(startSquare);
			case Piece.Bishop:
				return generateBishopMoves(startSquare);
			case Piece.Rook:
				return generateRookMoves(startSquare);
			case Piece.Knight:
				return generateKnightMoves(startSquare);
			case Piece.King:
				return MoveGeneratorKing.generateKingMoves(board, startSquare);
		}
		return new ArrayList<>();
	}
}
