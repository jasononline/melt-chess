package chess.gui.settings;

import java.util.Locale;

import chess.gui.Gui;
import chess.gui.util.TextManager;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

/**
 * Controls the behaviour and actions of the UI elements in the settings scene
 */
public class SettingsController {

	@FXML
	private AnchorPane rootPane;
	@FXML
	private Label titleLabel;
	@FXML
	private Label languageLabel;
	@FXML
	private Button englishButton;
	@FXML
	private Button germanButton;
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
	private Button cancelButton;
	@FXML
	private Button saveButton;

	private Locale currentLocale = SettingsModel.getCurrentLocale();

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
		TextManager.computeText(saveButton, "settings.save");
		TextManager.computeText(cancelButton, "settings.cancel");

		ChangeListener<Number> rootPaneSizeListener = (observable, oldValue, newValue) -> {
			resize();
		};
		rootPane.widthProperty().addListener(rootPaneSizeListener);
		rootPane.heightProperty().addListener(rootPaneSizeListener);

		if (SettingsModel.getCurrentLocale() == Locale.ENGLISH) {
			englishButton.getStyleClass().add("selected");
			germanButton.getStyleClass().remove("selected");
		} else {
			englishButton.getStyleClass().remove("selected");
			germanButton.getStyleClass().add("selected");
		}

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
	private void handleSaveButtonOnAction() {

		SettingsModel.setFlipBoard(flipBoardCheckbox.isSelected());
		SettingsModel.setOneTouchRule(oneTouchRuleCheckbox.isSelected());
		SettingsModel.setShowInCheck(showInCheckCheckbox.isSelected());
		SettingsModel.setShowPossibleMoves(showPossibleMovesCheckbox.isSelected());
		SettingsModel.setCurrentLocale(currentLocale);
		TextManager.setLocale(SettingsModel.getCurrentLocale());
		Gui.switchTo(SettingsModel.getLastScene());
	}

	@FXML
	private void handleLanguageButtonOnAction(ActionEvent event) {
		Button button = (Button) event.getSource();
		if (button == englishButton) {
			currentLocale = Locale.ENGLISH;
			germanButton.getStyleClass().remove("selected");
			englishButton.getStyleClass().add("selected");
		}
		if (button == germanButton) {
			currentLocale = Locale.GERMAN;
			englishButton.getStyleClass().remove("selected");
			germanButton.getStyleClass().add("selected");
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

	private void resize() {
		double rootHeight = rootPane.getHeight();
		double rootWidth = rootPane.getWidth();
		double titleFontSize = Math.min(rootHeight / 12, rootWidth / 21.33 * 1.2);
		double fontSize = Math.min(rootHeight / 28.8, rootWidth / 51.2 * 1.2);
		double langButtonFontSize = Math.min(rootHeight / 40, rootWidth / 71.11 * 1.2);
		double iconSize = Math.min(rootHeight / 20.57, rootWidth / 36.57 * 1.2);
		double borderRadius = Math.min(rootHeight / 72, rootWidth / 128 * 1.2);
		double borderWidth = Math.min(rootHeight / 240, rootWidth / 426.66);

		titleLabel.setStyle("-fx-font-size: " + titleFontSize);
		languageLabel.setStyle("-fx-font-size: " + fontSize);
		flipBoardLabel.setStyle("-fx-font-size: " + fontSize);
		oneTouchRuleLabel.setStyle("-fx-font-size: " + fontSize);
		showInCheckLabel.setStyle("-fx-font-size: " + fontSize);
		showPossibleMovesLabel.setStyle("-fx-font-size: " + fontSize);
		englishButton.setStyle("-fx-font-size: " + langButtonFontSize + "; -fx-background-radius: " + borderRadius / 2
				+ "; -fx-border-radius: " + borderRadius / 2 + "; -fx-border-width: " + borderWidth / 1.5);
		germanButton.setStyle("-fx-font-size: " + langButtonFontSize + "; -fx-background-radius: " + borderRadius / 2
				+ "; -fx-border-radius: " + borderRadius / 2 + "; -fx-border-width: " + borderWidth / 1.5);
		cancelButton.setStyle(
				"-fx-font-size: " + fontSize + "; -fx-graphic-text-gap: " + rootWidth / 42.66 + "; -fx-background-radius: "
						+ borderRadius + "; -fx-border-radius: " + borderRadius + "; -fx-border-width: " + borderWidth);
		saveButton.setStyle(
				"-fx-font-size: " + fontSize + "; -fx-graphic-text-gap: " + rootWidth / 42.66 + "; -fx-background-radius: "
						+ borderRadius + "; -fx-border-radius: " + borderRadius + "; -fx-border-width: " + borderWidth);

		saveButton.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius + "; -fx-border-radius: " + borderRadius);
		englishButton.getParent().getParent().getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius + "; -fx-border-radius: " + borderRadius);
		ImageView cancelIcon = (ImageView) cancelButton.getGraphic();
		ImageView saveIcon = (ImageView) saveButton.getGraphic();
		cancelIcon.setFitHeight(iconSize);
		saveIcon.setFitHeight(iconSize);

		setCheckboxStyle(flipBoardCheckbox, borderWidth / 1.5, borderRadius / 2, rootHeight);
		setCheckboxStyle(oneTouchRuleCheckbox, borderWidth / 1.5, borderRadius / 2, rootHeight);
		setCheckboxStyle(showInCheckCheckbox, borderWidth / 1.5, borderRadius / 2, rootHeight);
		setCheckboxStyle(showPossibleMovesCheckbox, borderWidth / 1.5, borderRadius / 2, rootHeight);

	}

	private void setCheckboxStyle(CheckBox checkbox, double borderWidth, double borderRadius, double rootHeight) {
		double width = checkbox.getHeight() != 0 ? checkbox.getHeight() * 2 : rootHeight / 20.57 * 2;
		double fontSize = rootHeight / 48;
		checkbox.setPrefWidth(width);
		checkbox.setMaxWidth(width);
		checkbox.setStyle(
				"-fx-padding: 0 0 0 " + (width / 3.04) + "; -fx-border-width: " + borderWidth + "; -fx-background-radius: "
						+ borderRadius + "; -fx-border-radius: " + borderRadius + "; -fx-font-size: " + fontSize);
	}

}