package chess.gui.settings;

import chess.gui.Gui;
import chess.gui.settings.SettingsModel.Language;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;

/**
 * Controls the behaviour and actions of the UI elements in the settings scene
 */
public class SettingsController {

	@FXML
	private Label langLabel;
	@FXML
	private Label flipBoardLabel;
	@FXML
	private Label oneTouchRuleLabel;
	@FXML
	private Label showInCheckLabel;
	@FXML
	private Label showPossibleMovesLabel;
	@FXML
	private RadioButton enRadioButton;
	@FXML
	private RadioButton deRadioButton;
	@FXML
	private CheckBox flipBoardCheckbox;
	@FXML
	private CheckBox oneTouchRuleCheckbox;
	@FXML
	private CheckBox showInCheckCheckbox;
	@FXML
	private CheckBox showPossibleMovesCheckbox;

	@FXML
	private void initialize() {
		deRadioButton.setSelected(SettingsModel.getCurrentLanguage() == Language.DE);
		enRadioButton.setSelected(SettingsModel.getCurrentLanguage() == Language.EN);
		flipBoardCheckbox.setSelected(SettingsModel.isFlipBoard());
		oneTouchRuleCheckbox.setSelected(SettingsModel.isOneTouchRule());
		showInCheckCheckbox.setSelected(SettingsModel.isShowInCheck());
		showPossibleMovesCheckbox.setSelected(SettingsModel.isShowPossibleMoves());
	}

	/**
	 * Controls the effect when clicking the saveButton. Current settings will be
	 * overwritten with new ones, SettingsView will be closed.
	 */
	@FXML
	private void handleButtonSaveOnAction() {

		SettingsModel.setFlipBoard(flipBoardCheckbox.isSelected());
		SettingsModel.setOneTouchRule(oneTouchRuleCheckbox.isSelected());
		SettingsModel.setShowInCheck(showInCheckCheckbox.isSelected());
		SettingsModel.setShowPossibleMoves(showPossibleMovesCheckbox.isSelected());
		SettingsModel.setCurrentLanguage(enRadioButton.isSelected() ? Language.EN : Language.DE);

		initialize();
		exitSettings();

		// System.out.println("-----------------------------------");
		// System.out.println("Flip Board: " + SettingsModel.isFlipBoard());
		// System.out.println("One Touch Rule: " + SettingsModel.isOneTouchRule());
		// System.out.println("Show In Check: " + SettingsModel.isShowInCheck());
		// System.out.println("Show Possibli Moves: " +
		// SettingsModel.isShowPossibleMoves());
		// System.out.println("Current Language: " +
		// SettingsModel.getCurrentLanguage());
		// System.out.println("Settings saved");
		// System.out.println("-----------------------------------");
	}

	/**
	 * Controls the effect when clicking the saveButton. Current settings won't be
	 * overwritten, SettingsView will be closed.
	 */
	@FXML
	private void handleButtonCancelOnAction() {
		initialize();
		exitSettings();
	}

	private void exitSettings() {
		switch (SettingsModel.getLastScene()) {
			case Menu:
				Gui.switchToMenu();
				break;
			case Game:
				Gui.switchToGame();
				break;
		}
	}

}