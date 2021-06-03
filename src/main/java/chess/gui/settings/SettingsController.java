package chess.gui.settings;

import java.util.Locale;

import chess.gui.Gui;
import chess.gui.util.TextManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Label;

/**
 * Controls the behaviour and actions of the UI elements in the settings scene
 */
public class SettingsController {

	private boolean computed = false;

	@FXML
	private Label titleLabel;
	@FXML
	private Label langLabel;
	@FXML
	private RadioButton enRadioButton;
	@FXML
	private RadioButton deRadioButton;
	@FXML
	private Label flipBoardLabel;
	@FXML
	private CheckBox flipBoardCheckbox;
	@FXML
	private Label oneTouchRuleLabel;
	@FXML
	private CheckBox oneTouchRuleCheckbox;
	@FXML
	private Label showInCheckLabel;
	@FXML
	private CheckBox showInCheckCheckbox;
	@FXML
	private Label showPossibleMovesLabel;
	@FXML
	private CheckBox showPossibleMovesCheckbox;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;

	@FXML
	private void initialize() {
		if (!computed) {
			TextManager.computeText(titleLabel, "settings.title");
			TextManager.computeText(langLabel, "settings.language");
			TextManager.computeText(enRadioButton, "settings.en");
			TextManager.computeText(deRadioButton, "settings.de");
			TextManager.computeText(flipBoardLabel, "settings.flipBoard");
			TextManager.computeText(oneTouchRuleLabel, "settings.oneTouchRule");
			TextManager.computeText(showInCheckLabel, "settings.showInCheck");
			TextManager.computeText(showPossibleMovesLabel, "settings.showPossibleMoves");
			TextManager.computeText(saveButton, "settings.save");
			TextManager.computeText(cancelButton, "settings.cancel");
			computed = true;
		}

		String diactivate = TextManager.get("settings.diactivate");
		String activate = TextManager.get("settings.activate");

		deRadioButton.setSelected(SettingsModel.getCurrentLocale() == Locale.GERMAN);
		enRadioButton.setSelected(SettingsModel.getCurrentLocale() == Locale.ENGLISH);
		flipBoardCheckbox.setSelected(SettingsModel.isFlipBoard());
		flipBoardCheckbox.setText(flipBoardCheckbox.isSelected() ? diactivate : activate);
		oneTouchRuleCheckbox.setSelected(SettingsModel.isOneTouchRule());
		oneTouchRuleCheckbox.setText(oneTouchRuleCheckbox.isSelected() ? diactivate : activate);
		showInCheckCheckbox.setSelected(SettingsModel.isShowInCheck());
		showInCheckCheckbox.setText(showInCheckCheckbox.isSelected() ? diactivate : activate);
		showPossibleMovesCheckbox.setSelected(SettingsModel.isShowPossibleMoves());
		showPossibleMovesCheckbox.setText(showPossibleMovesCheckbox.isSelected() ? diactivate : activate);
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
		SettingsModel.setCurrentLocale(enRadioButton.isSelected() ? Locale.ENGLISH : Locale.GERMAN);
		TextManager.setLocale(SettingsModel.getCurrentLocale());
		initialize();
		exitSettings();
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

	@FXML
	private void handleCheckboxOnAction() {
		String diactivate = TextManager.get("settings.diactivate");
		String activate = TextManager.get("settings.activate");
		flipBoardCheckbox.setText(flipBoardCheckbox.isSelected() ? diactivate : activate);
		oneTouchRuleCheckbox.setText(oneTouchRuleCheckbox.isSelected() ? diactivate : activate);
		showInCheckCheckbox.setText(showInCheckCheckbox.isSelected() ? diactivate : activate);
		showPossibleMovesCheckbox.setText(showPossibleMovesCheckbox.isSelected() ? diactivate : activate);
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