package chess.gui.settings;

import java.util.Locale;

import chess.gui.Gui;
import chess.gui.util.ResizeManager;
import chess.gui.util.TextManager;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

/**
 * Controls the behaviour and actions of the UI elements in the settings scene
 */
@SuppressWarnings({"PMD.UnusedPrivateMethod", "PMD.TooManyFields"})
// Some methods in this class seem unused but they are used by FXML
// This class controls many elements of the gui, hence many fields are needed here.
public class SettingsController {

	@FXML
	public AnchorPane rootPane;
	@FXML
	public Label titleLabel;
	@FXML
	public Label languageLabel;
	@FXML
	public Button englishButton;
	@FXML
	public Button germanButton;
	@FXML
	public Label flipBoardLabel;
	@FXML
	public CheckBox flipBoardCheckbox;
	@FXML
	public Label oneTouchRuleLabel;
	@FXML
	public CheckBox oneTouchRuleCheckbox;
	@FXML
	public Label showInCheckLabel;
	@FXML
	public CheckBox showInCheckCheckbox;
	@FXML
	public Label showPossibleMovesLabel;
	@FXML
	public CheckBox showPossibleMovesCheckbox;
	@FXML
	public Label soundEffectsLabel;
	@FXML
	public CheckBox soundEffectsCheckbox;
	@FXML
	public Button cancelButton;
	@FXML
	public Button saveButton;

	private ResizeManager resizeManager = new ResizeManager(this);

	private Locale currentLocale = SettingsModel.getCurrentLocale();
	private static String selected = "selected";

	@FXML
	private void initialize() {
		TextManager.computeText(titleLabel, "settings.title");
		TextManager.computeText(languageLabel, "settings.language");
		TextManager.computeText(englishButton, "settings.en");
		TextManager.computeText(germanButton, "settings.de");
		TextManager.computeText(flipBoardLabel, "settings.flipBoard");
		TextManager.computeText(oneTouchRuleLabel, "settings.oneTouchRule");
		TextManager.computeText(showInCheckLabel, "settings.showInCheck");
		TextManager.computeText(showPossibleMovesLabel, "settings.showPossibleMoves");
		TextManager.computeText(soundEffectsLabel, "settings.soundEffects");
		TextManager.computeText(saveButton, "settings.save");
		TextManager.computeText(cancelButton, "settings.cancel");

		ChangeListener<Number> rootPaneSizeListener = (observable, oldValue, newValue) -> {
			resizeManager.resizeSettings(rootPane.getWidth(), rootPane.getHeight());
		};
		rootPane.widthProperty().addListener(rootPaneSizeListener);
		rootPane.heightProperty().addListener(rootPaneSizeListener);

		if (SettingsModel.getCurrentLocale() == Locale.ENGLISH) {
			englishButton.getStyleClass().add(selected);
			germanButton.getStyleClass().remove(selected);
		} else {
			englishButton.getStyleClass().remove(selected);
			germanButton.getStyleClass().add(selected);
		}

		flipBoardCheckbox.setSelected(SettingsModel.isFlipBoard());
		oneTouchRuleCheckbox.setSelected(SettingsModel.isOneTouchRule());
		showInCheckCheckbox.setSelected(SettingsModel.isShowInCheck());
		showPossibleMovesCheckbox.setSelected(SettingsModel.isShowPossibleMoves());
		soundEffectsCheckbox.setSelected(SettingsModel.isSoundEffects());
	}

	/**
	 * Controls the effect when clicking the saveButton. Current settings will be
	 * overwritten with new ones, SettingsView will be closed.
	 */
	@FXML
	private void handleSaveButtonOnAction() {

		SettingsModel.setFlipBoard(flipBoardCheckbox.isSelected());
		SettingsModel.setOneTouchRule(oneTouchRuleCheckbox.isSelected());
		SettingsModel.setShowInCheck(showInCheckCheckbox.isSelected());
		SettingsModel.setShowPossibleMoves(showPossibleMovesCheckbox.isSelected());
		SettingsModel.setSoundEffects(soundEffectsCheckbox.isSelected());
		SettingsModel.setCurrentLocale(currentLocale);
		TextManager.setLocale(SettingsModel.getCurrentLocale());
		Gui.switchTo(SettingsModel.getLastScene());
	}

	@FXML
	private void handleLanguageButtonOnAction(ActionEvent event) {
		Button button = (Button) event.getSource();
		if (button == englishButton) {
			currentLocale = Locale.ENGLISH;
			germanButton.getStyleClass().remove(selected);
			englishButton.getStyleClass().add(selected);
		}
		if (button == germanButton) {
			currentLocale = Locale.GERMAN;
			englishButton.getStyleClass().remove(selected);
			germanButton.getStyleClass().add(selected);
		}
		TextManager.setLocale(currentLocale);
	}

	/**
	 * Controls the effect when clicking the saveButton. Current settings won't be
	 * overwritten, SettingsView will be closed.
	 */
	@FXML
	private void handleCancelButtonOnAction() {
		TextManager.setLocale(SettingsModel.getCurrentLocale());
		Gui.switchTo(SettingsModel.getLastScene());
	}
}