package chess.gui.settings;

import java.io.Serializable;

/**
 * Data container for all the settings. Should implement Serializable to save the settings.
 */
// if this stays static may use Externalizable instead?
public class SettingsModel implements Serializable {

    private static boolean flipBoard = false;
    private static boolean oneTouchRule = false;
    private static boolean showInCheck = false;
    private static boolean showPossibleMoves = false;
    private static String currentLanguage = "default";

    public static boolean isFlipBoard() {
        return flipBoard;
    }

    public static void setFlipBoard(boolean flipBoard) {
        SettingsModel.flipBoard = flipBoard;
    }

    public static boolean isOneTouchRule() {
        return oneTouchRule;
    }

    public static void setOneTouchRule(boolean oneTouchRule) {
        SettingsModel.oneTouchRule = oneTouchRule;
    }

    public static boolean isShowInCheck() {
        return showInCheck;
    }

    public static void setShowInCheck(boolean showInCheck) {
        SettingsModel.showInCheck = showInCheck;
    }

    public static boolean isShowPossibleMoves() {
        return showPossibleMoves;
    }

    public static void setShowPossibleMoves(boolean showPossibleMoves) {
        SettingsModel.showPossibleMoves = showPossibleMoves;
    }

    public static String getCurrentLanguage() {
        return currentLanguage;
    }

    public static void setCurrentLanguage(String currentLanguage) {
        SettingsModel.currentLanguage = currentLanguage;
    }
}
