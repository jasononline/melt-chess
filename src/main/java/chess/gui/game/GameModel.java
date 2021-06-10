package chess.gui.game;

import chess.engine.Engine;
import chess.gui.util.GraphicsManager;
import chess.model.Game;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

import chess.model.Move;
import chess.model.Piece;

/**
 * Storage for information on the currently visible board position, like
 * positions of image objects, which piece is selected, etc.
 */
public class GameModel {

	/**
	 * The current Game
	 */
	private static chess.model.Game currentGame;

	private static Engine engine = new Engine();

	/*
	 * Enumeration of available game modes
	 */
	public enum ChessMode {
		None, Player, Computer, Network;
	}

	/*
	 * Enumeration of available colors
	 */
	public enum ChessColor {
		None, White, Black;
	}

	/**
	 * Stores the chosen game mode
	 */
	private static ChessMode gameMode = ChessMode.None;

	/**
	 * Stores the chosen color (not to confuse with currentColor)
	 */
	private static ChessColor color = ChessColor.None;

	/**
	 * Stores the Move objects that represent the history of the game.
	 */
	private static List<Move> movesHistory = new ArrayList<>();

	/**
	 * Stores the ImageView objects that represent the beaten white pieces.
	 */
	private static List<ImageView> beatenWhitePiecesGraphics = new ArrayList<>();

	/**
	 * Stores the ImageView objects that represent the beaten black pieces.
	 */
	private static List<ImageView> beatenBlackPiecesGraphics = new ArrayList<>();

	/**
	 * Stores the index of the selected field according to model.Board.squares. -1
	 * means "no field selected". "a8" is 0 and "h1" is 63.
	 */
	private static int selectedIndex = -1;

	/**
	 * Begins a new Game
	 */
	public static void beginNewGame() {
		// clear outdated lists
		GameModel.beatenWhitePiecesGraphics.clear();
		GameModel.beatenBlackPiecesGraphics.clear();
		GameModel.movesHistory.clear();
		// start the new game
		currentGame = new Game("k4bnr/3Ppppp/7q/1R6/1R6/8/PP1PPPPP/1NBQKBNR");
		if (color == ChessColor.Black) {
			currentGame.getCurrentPosition().setTurnColor(Piece.Black);
		}
	}

	/**
	 * Updates the Lists of the beaten pieces
	 */
	public static void updateBeatenPiecesLists() {
		List<Integer> capturedPieces = currentGame.getCurrentPosition().getCapturedPieces();
		beatenBlackPiecesGraphics.clear();
		beatenWhitePiecesGraphics.clear();
		ImageView pieceView;
		for (int piece: capturedPieces) {
			pieceView = GraphicsManager.getGraphicAsImageView(Piece.toName(piece));
			if (Piece.isColor(piece, Piece.White)) {
				beatenWhitePiecesGraphics.add(pieceView);
			} else {
				beatenBlackPiecesGraphics.add(pieceView);
			}
		}
	}

	/**
	 * Performs a move that is computed by the engine
	 */
	public static void performEngineMove() {
		Move next = engine.generateBestMove(currentGame.getCurrentPosition());
		currentGame.attemptMove(next);
	}

	/**
	 * Gives information about whether a piece has been selected or not
	 * 
	 * @return true if a piece has been selected
	 */
	public static boolean isSelected() {
		return selectedIndex != -1;
	}

	/**
	 * Setter for the gameMode
	 * 
	 * @param mode the new game mode
	 */
	public static void setGameMode(ChessMode mode) {
		GameModel.gameMode = mode;
	}

	/**
	 * Setter for the color
	 * 
	 * @param color the new color
	 */
	public static void setColor(ChessColor color) {
		GameModel.color = color;
	}

	/**
	 * Setter for the selectedIndex
	 * 
	 * @param index the new selectedIndex, should be -1 smaller than or equal index
	 *              smaller than or equal 63.
	 */
	public static void setSelectedIndex(int index) {
		if (-1 <= index && index <= 63) {
			GameModel.selectedIndex = index;
		}
	}

	/**
	 * Getter for the currentGame variable
	 *
	 * @return the current game
	 */
	public static Game getCurrentGame() {
		return currentGame;
	}

	/**
	 * Getter for the current game mode
	 * 
	 * @return current game mode
	 */
	public static ChessMode getGameMode() {
		return gameMode;
	}

	/**
	 * Getter for the current color
	 * 
	 * @return current color
	 */
	public static ChessColor getColor() {
		return color;
	}

	/**
	 * Getter for the movesHistory-List
	 * 
	 * @return the movesHistory-List
	 */
	public static List<Move> getMovesHistory() {
		return movesHistory;
	}

	/**
	 * Getter for the BeatenPiecesGraphics-List
	 * 
	 * @return the BeatenPiecesGraphics-List
	 */
	public static List<ImageView> getBeatenWhitePiecesGraphics() {
		return beatenWhitePiecesGraphics;
	}

	/**
	 * Getter for the BeatenPiecesGraphics-List
	 * 
	 * @return the BeatenPiecesGraphics-List
	 */
	public static List<ImageView> getBeatenBlackPiecesGraphics() {
		return beatenBlackPiecesGraphics;
	}

	/**
	 * Getter for the selected index
	 * 
	 * @return the selected index
	 */
	public static int getSelectedIndex() {
		return selectedIndex;
	}
}
