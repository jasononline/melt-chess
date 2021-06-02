package chess.gui.settings;

import chess.gui.Gui;
import chess.gui.settings.SettingsModel.Language;
import javafx.fxml.FXML;

/**
 * Controls the behaviour and actions of the UI elements in the settings scene
 */
public class SettingsController {

	private boolean flipBoard = false;
	private boolean oneTouchRule = false;
	private boolean showInCheck = false;
	private boolean showPossibleMoves = false;
	private Language currentLanguage = Language.EN;

	/**
	 * Controls the effect when clicking the saveButton. Current settings will be
	 * overwritten with new ones, SettingsView will be closed.
	 */
	@FXML
	private void handleButtonSaveOnAction() {

		SettingsModel.setFlipBoard(flipBoard);
		SettingsModel.setOneTouchRule(oneTouchRule);
		SettingsModel.setShowInCheck(showInCheck);
		SettingsModel.setShowPossibleMoves(showPossibleMoves);
		SettingsModel.setCurrentLanguage(currentLanguage);

		System.out.println("-----------------------------------");
		System.out.println("Flip Board: " + SettingsModel.isFlipBoard());
		System.out.println("One Touch Rule: " + SettingsModel.isOneTouchRule());
		System.out.println("Show In Check: " + SettingsModel.isShowInCheck());
		System.out.println("Show Possibli Moves: " + SettingsModel.isShowPossibleMoves());
		System.out.println("Current Language: " + SettingsModel.getCurrentLanguage());
		System.out.println("Settings saved");
		System.out.println("-----------------------------------");

		switch (SettingsModel.getLastScene()) {
			case Menu:
				Gui.switchToMenu();
			case Game:
				Gui.switchToGame();
		}

	}

	/**
	 * Controls the effect when clicking the saveButton. Current settings won't be
	 * overwritten, SettingsView will be closed.
	 */
	@FXML
	private void handleButtonCancelOnAction() {
		switch (SettingsModel.getLastScene()) {
			case Menu:
				Gui.switchToMenu();
			case Game:
				Gui.switchToGame();
		}
	}

	/**
	 * Controls the effect when clicking the radioButtonEn. The currentLanguage
	 * variable will be set by this function.
	 */
	@FXML
	private void handleRadioButtonEnOnAction() {
		this.currentLanguage = Language.EN;
	}

	/**
	 * Controls the effect when clicking the radioButtonDe. The currentLanguage
	 * variable will be set by this function.
	 */
	@FXML
	private void handleRadioButtonDeOnAction() {
		this.currentLanguage = Language.DE;
	}

	/**
	 * Controls the effect when clicking the checkboxFlipBoard. The flipBoard
	 * variable will be set by this function.
	 */
	@FXML
	private void handleCheckboxFlipBoardOnAction() {
		this.flipBoard = !SettingsModel.isFlipBoard();
	}

	/**
	 * Controls the effect when clicking the checkboxOneTouchRule. The oneTouchRule
	 * variable will be set by this function.
	 */
	@FXML
	private void handleCheckboxOneTouchRuleOnAction() {
		this.oneTouchRule = !SettingsModel.isOneTouchRule();
	}

	/**
	 * Controls the effect when clicking the checkboxShowInCheck. The showInCheck
	 * variable will be set by this function.
	 */
	@FXML
	private void handleCheckboxShowInCheckOnAction() {
		this.showInCheck = !SettingsModel.isShowInCheck();
	}

	/**
	 * Controls the effect when clicking the checkboxShowPossibleMoves. The
	 * showPossibleMoves variable will be set by this function.
	 */
	@FXML
	private void handleCheckboxShowPossibleMovesOnAction() {
		this.showPossibleMoves = !SettingsModel.isShowPossibleMoves();
	}
}
