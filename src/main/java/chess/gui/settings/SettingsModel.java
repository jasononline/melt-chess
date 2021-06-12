package chess.gui.settings;

import java.io.Serializable;
import java.util.Locale;

import chess.gui.Gui.ChessScene;

/**
 * Data container for all the settings. Should implement Serializable to save
 * the settings.
 */
// if this stays static may use Externalizable instead?
public class SettingsModel implements Serializable {

	private static boolean flipBoard = false;
	private static boolean oneTouchRule = false;
	private static boolean showInCheck = true;
	private static boolean showPossibleMoves = true;
	private static boolean soundEffects = true;
	private static Locale currentLocale = Locale.GERMAN;
	private static ChessScene lastScene = ChessScene.Menu;

	/**
	 * @return boolean value whether the board flips after each move
	 */
	public static boolean isFlipBoard() {
		return flipBoard;
	}

	/**
	 * Set whether the board flips after each move
	 * 
	 * @param flipBoard boolean value whether the board flips after each move
	 */
	public static void setFlipBoard(boolean flipBoard) {
		SettingsModel.flipBoard = flipBoard;
	}

	/**
	 * @return boolean value whether the one-touch rule applies
	 */
	public static boolean isOneTouchRule() {
		return oneTouchRule;
	}

	/**
	 * Set whether the one-touch rule applies
	 * 
	 * @param oneTouchRule boolean value whether the one-touch rule applies
	 */
	public static void setOneTouchRule(boolean oneTouchRule) {
		SettingsModel.oneTouchRule = oneTouchRule;
	}

	/**
	 * @return boolean value whether to show the user a warning that he is in check
	 */
	public static boolean isShowInCheck() {
		return showInCheck;
	}

	/**
	 * Set whether to show the user a warning that he is in check
	 * 
	 * @param showInCheck boolean value whether to show the user a warning that he
	 *                    is in check
	 */
	public static void setShowInCheck(boolean showInCheck) {
		SettingsModel.showInCheck = showInCheck;
	}

	/**
	 * @return boolean value whether to show the user his possible moves
	 */
	public static boolean isShowPossibleMoves() {
		return showPossibleMoves;
	}

	/**
	 * Set whether to show the user his possible moves
	 * 
	 * @param showPossibleMoves boolean value whether to show the user his possible
	 *                          moves
	 */
	public static void setShowPossibleMoves(boolean showPossibleMoves) {
		SettingsModel.showPossibleMoves = showPossibleMoves;
	}

	/**
	 * @return boolean value whether sound effects is enabled
	 */
	public static boolean isSoundEffects() {
		return soundEffects;
	}

	/**
	 * Set whether sound effects is enabled
	 * 
	 * @param soundEffects boolean value whether sound effects is enabled
	 */
	public static void setSoundEffects(boolean soundEffects) {
		SettingsModel.soundEffects = soundEffects;
	}

	/**
	 * @return current interface locale
	 */
	public static Locale getCurrentLocale() {
		return currentLocale;
	}

	/**
	 * Set current interface locale
	 * 
	 * @param currentLocale current interface locale
	 */
	public static void setCurrentLocale(Locale currentLocale) {
		SettingsModel.currentLocale = currentLocale;
	}

	/**
	 * @return scene from which the settings view was called
	 */
	public static ChessScene getLastScene() {
		return lastScene;
	}

	/**
	 * Set scene from which the settings view was called
	 * 
	 * @param lastScene scene from which the settings view was called
	 */
	public static void setLastScene(ChessScene lastScene) {
		SettingsModel.lastScene = lastScene;
	}
}
