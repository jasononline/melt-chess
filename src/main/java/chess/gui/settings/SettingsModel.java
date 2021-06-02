package chess.gui.settings;

import java.io.Serializable;

/**
 * Data container for all the settings. Should implement Serializable to save
 * the settings.
 */
// if this stays static may use Externalizable instead?
public class SettingsModel implements Serializable {

	/**
	 * Enumeration of supported languages
	 */
	public enum Language {
		EN, DE;
	}

	/**
	 * Enumeration of scenes from which the settings view can be called
	 */
	public enum LastScene {
		Menu, Game;
	}

	private static boolean flipBoard = false;
	private static boolean oneTouchRule = false;
	private static boolean showInCheck = false;
	private static boolean showPossibleMoves = false;
	private static Language currentLanguage = Language.DE;
	private static LastScene lastSceneName = LastScene.Menu;

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
	 * @return current interface language
	 */
	public static Language getCurrentLanguage() {
		return currentLanguage;
	}

	/**
	 * Set current interface language
	 * 
	 * @param currentLanguage current interface language
	 */
	public static void setCurrentLanguage(Language currentLanguage) {
		SettingsModel.currentLanguage = currentLanguage;
	}

	/**
	 * @return scene from which the settings view was called
	 */
	public static LastScene getLastSceneName() {
		return lastSceneName;
	}

	/**
	 * Set scene from which the settings view was called
	 * 
	 * @param lastSceneName scene from which the settings view was called
	 */
	public static void setLastSceneName(LastScene lastSceneName) {
		SettingsModel.lastSceneName = lastSceneName;
	}
}
