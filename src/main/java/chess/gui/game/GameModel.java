package chess.gui.game;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

import chess.model.Move;

/**
 * Storage for information on the currently visible board position, like
 * positions of image objects, which piece is selected, etc.
 */
public class GameModel {

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
	 * Stroes the chosen color
	 */
	private static ChessColor color = ChessColor.None;

	/**
	 * Stores the Move objects that represent the history of the game.
	 */
	private static List<Move> movesHistory = new ArrayList<>();

	/**
	 * Stores the ImageView objects that represent the pieces on the field.
	 */
	private static List<ImageView> piecesGraphics = new ArrayList<>();

	/**
	 * Stores the ImageView objects that represent the beaten white pieces.
	 */
	private static List<ImageView> beatenWhitePiecesGraphics = new ArrayList<>();

	/**
	 * Stores the ImageView objects that represent the beaten black pieces.
	 */
	private static List<ImageView> beatenBlackPiecesGraphics = new ArrayList<>();

	/**
	 * Stores the ImageView objects that represent the possible moves on the field.
	 */
	private static List<ImageView> possibleMovesGraphics = new ArrayList<>();

	/**
	 * Stored the ImageView object that represents the selection of a piece.
	 */
	private static ImageView selectedFieldGraphic = null;

	/**
	 * Stores the index of the selected field according to model.Board.squares. -1
	 * means "no field selected". "a8" is 0 and "h1" is 63.
	 */
	private static int selectedIndex = -1;

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
	 * @param mode the new color
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
	 * Getter for the piecesGraphics-List
	 * 
	 * @return the piecesGraphics-List
	 */
	public static List<ImageView> getPiecesGraphics() {
		return piecesGraphics;
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
	 * Getter for the possibleMovesGraphics-List
	 * 
	 * @return the possibleMovesGraphics-List
	 */
	public static List<ImageView> getPossibleMovesGraphics() {
		return possibleMovesGraphics;
	}

	/**
	 * Getter for the selected index
	 * 
	 * @return the selected index
	 */
	public static int getSelectedIndex() {
		return selectedIndex;
	}

	/**
	 * Getter for the selectedFieldGraphic
	 * 
	 * @return the selectedFieldGraphic
	 */
	public static ImageView getSelectedFieldGraphic() {
		return selectedFieldGraphic;
	}

	/**
	 * Setter for the piecesGraphics-List
	 * 
	 * @param piecesGraphics the new piecesGraphics-List
	 */
	public static void setPiecesGraphics(List<ImageView> piecesGraphics) {
		GameModel.piecesGraphics = piecesGraphics;
	}

	/**
	 * Setter for the possibleMovesGraphics-List
	 * 
	 * @param possibleMovesGraphics the new possibleMovesGraphics-List
	 */
	public static void setPossibleMovesGraphics(List<ImageView> possibleMovesGraphics) {
		GameModel.possibleMovesGraphics = possibleMovesGraphics;
	}

	/**
	 * Setter for the selectedFieldGraphic-List
	 * 
	 * @param selectedFieldGraphic the new selectedFieldGraphic-List
	 */
	public static void setSelectedFieldGraphic(ImageView selectedFieldGraphic) {
		GameModel.selectedFieldGraphic = selectedFieldGraphic;
	}

	public static void reset() {
		// TODO: Reset all to default values
		GameModel.beatenWhitePiecesGraphics.clear();
		GameModel.beatenBlackPiecesGraphics.clear();
		GameModel.movesHistory.clear();
	}
}
