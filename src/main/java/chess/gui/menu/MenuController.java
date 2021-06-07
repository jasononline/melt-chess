package chess.gui.menu;

import chess.gui.Gui;
import chess.gui.settings.SettingsModel;
import chess.gui.util.TextManager;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * Controls the behaviour and actions of the UI elements in the menu scene,
 * mainly switching to one of the other scenes and selecting game options.
 */
public class MenuController {

	/*
	 * Enumeration of available game modes
	 */
	private enum ChessMode {
		None, Player, Computer, Network;
	}

	/*
	 * Enumeration of available colors
	 */
	private enum ChessColor {
		None, White, Black;
	}

	/**
	 * Stores the chosen game mode
	 */
	private ChessMode gameMode = ChessMode.None;

	/**
	 * Stroes the chosen color
	 */
	private ChessColor color = ChessColor.None;

	@FXML
	private AnchorPane rootPane;
	@FXML
	private Label titleLabel;
	@FXML
	private Label modeLabel;
	@FXML
	private Button playerModeButton;
	@FXML
	private Button aiModeButton;
	@FXML
	private Button networkModeButton;
	@FXML
	private AnchorPane colorPane;
	@FXML
	private Label colorLabel;
	@FXML
	private Button whiteColorButton;
	@FXML
	private Button blackColorButton;
	@FXML
	private Button startButton;
	@FXML
	private Button settingsButton;
	@FXML
	private Button quitButton;

	@FXML
	private void initialize() {
		TextManager.computeText(titleLabel, "menu.title");
		TextManager.computeText(modeLabel, "menu.mode");
		TextManager.computeText(playerModeButton, "menu.pvpMode");
		TextManager.computeText(aiModeButton, "menu.aiMode");
		TextManager.computeText(networkModeButton, "menu.netMode");
		TextManager.computeText(colorLabel, "menu.color");
		TextManager.computeText(whiteColorButton, "menu.white");
		TextManager.computeText(blackColorButton, "menu.black");
		TextManager.computeText(settingsButton, "menu.settings");
		TextManager.computeText(startButton, "menu.start");
		TextManager.computeText(quitButton, "menu.quit");
		startButton.setDisable(true);
		colorPane.setDisable(true);

		ChangeListener<Number> rootPaneSizeListener = (observable, oldValue, newValue) -> {
			resize();
		};
		rootPane.widthProperty().addListener(rootPaneSizeListener);
		rootPane.heightProperty().addListener(rootPaneSizeListener);
	}

	/**
	 * Controls the effect when clicking one of the mode buttons. The enemChoice
	 * variable will be set by this function.
	 */
	@FXML
	private void handleModeButtonOnAction(ActionEvent event) {
		Node source = (Node) event.getSource();
		playerModeButton.getStyleClass().remove("selected");
		aiModeButton.getStyleClass().remove("selected");
		networkModeButton.getStyleClass().remove("selected");
		colorPane.setDisable(true);
		source.getStyleClass().add("selected");

		if (source == playerModeButton) {
			whiteColorButton.getStyleClass().remove("selected");
			blackColorButton.getStyleClass().remove("selected");
			this.color = ChessColor.None;
			this.gameMode = ChessMode.Player;
		}
		if (source == aiModeButton) {
			colorPane.setDisable(false);
			this.gameMode = ChessMode.Computer;
		}
		if (source == networkModeButton) {
			whiteColorButton.getStyleClass().remove("selected");
			blackColorButton.getStyleClass().remove("selected");
			this.color = ChessColor.None;
			this.gameMode = ChessMode.Network;
		}

		if (this.gameMode != ChessMode.None && this.gameMode != ChessMode.Computer) {
			startButton.setDisable(false);
		} else {
			startButton.setDisable(true);
		}

	}

	/**
	 * Controls the effect when clicking one of the color buttons. The color
	 * variable will be set by this function.
	 */
	@FXML
	private void handleColorButtonOnAction(ActionEvent event) {
		Node source = (Node) event.getSource();
		whiteColorButton.getStyleClass().remove("selected");
		blackColorButton.getStyleClass().remove("selected");
		source.getStyleClass().add("selected");

		if (source == whiteColorButton)
			this.color = ChessColor.White;
		if (source == blackColorButton)
			this.color = ChessColor.Black;

		if (this.color != ChessColor.None) {
			startButton.setDisable(false);
		}
	}

	/**
	 * Controls the effect when clicking one of the menu buttons.
	 */
	@FXML
	private void handleMenuButtonOnAction(ActionEvent event) {
		Node source = (Node) event.getSource();

		if (source == startButton) {
			if (this.gameMode == ChessMode.Network) {
				Gui.switchTo(Gui.ChessScene.NetworkConnection);
			} else {
				// TODO start a new Game
				Gui.switchTo(Gui.ChessScene.Game);
			}

		}

		if (source == settingsButton) {
			SettingsModel.setLastScene(Gui.ChessScene.Menu);
			Gui.switchTo(Gui.ChessScene.Settings);
		}

		if (source == quitButton)
			System.exit(0);
	}

	private void resize() {
		double rootHeight = rootPane.getHeight();
		double rootWidth = rootPane.getWidth();
		double titleFontSize = Math.min(rootHeight / 12, rootWidth / 21.33 * 1.2);
		double boxNameFontSize = Math.min(rootHeight / 18, rootWidth / 32 * 1.2);
		double iconSize = Math.min(rootHeight / 20.57, rootWidth / 36.57 * 1.2);
		double borderRadius = Math.min(rootHeight / 72, rootWidth / 128 * 1.2);

		titleLabel.setStyle("-fx-font-size: " + titleFontSize);
		modeLabel.setStyle("-fx-font-size: " + boxNameFontSize);
		colorLabel.setStyle("-fx-font-size: " + boxNameFontSize);
		setButtonStyle(playerModeButton, new double[] { rootWidth, rootHeight }, rootHeight / 36);
		setButtonStyle(aiModeButton, new double[] { rootWidth, rootHeight }, rootHeight / 36);
		setButtonStyle(networkModeButton, new double[] { rootWidth, rootHeight }, rootHeight / 36);
		setButtonStyle(whiteColorButton, new double[] { rootWidth, rootHeight }, rootHeight / 36);
		setButtonStyle(blackColorButton, new double[] { rootWidth, rootHeight }, rootHeight / 36);
		setButtonStyle(startButton, new double[] { rootWidth, rootHeight }, rootWidth / 42.66);
		setButtonStyle(settingsButton, new double[] { rootWidth, rootHeight }, rootWidth / 42.66);
		setButtonStyle(quitButton, new double[] { rootWidth, rootHeight }, rootWidth / 42.66);
		ImageView playerModeIcon = (ImageView) playerModeButton.getGraphic();
		ImageView aiModeIcon = (ImageView) aiModeButton.getGraphic();
		ImageView networkModeIcon = (ImageView) networkModeButton.getGraphic();
		ImageView whiteColorIcon = (ImageView) whiteColorButton.getGraphic();
		ImageView blackColorIcon = (ImageView) blackColorButton.getGraphic();
		ImageView startIcon = (ImageView) startButton.getGraphic();
		ImageView settingsIcon = (ImageView) settingsButton.getGraphic();
		ImageView quitIcon = (ImageView) quitButton.getGraphic();
		playerModeIcon.setFitHeight(iconSize);
		aiModeIcon.setFitHeight(iconSize);
		networkModeIcon.setFitHeight(iconSize);
		whiteColorIcon.setFitHeight(iconSize);
		blackColorIcon.setFitHeight(iconSize);
		startIcon.setFitHeight(iconSize);
		settingsIcon.setFitHeight(iconSize);
		quitIcon.setFitHeight(iconSize);
		playerModeButton.getParent().getParent().getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius + "; -fx-border-radius: " + borderRadius);
		whiteColorButton.getParent().getParent().getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius + "; -fx-border-radius: " + borderRadius);
		startButton.getParent().getParent().getParent()
				.setStyle("-fx-background-radius: " + borderRadius + "; -fx-border-radius: " + borderRadius);
	}

	private void setButtonStyle(Button button, double[] rootSize, double graphicTextGap) {
		double fontSize = Math.min(rootSize[1] / 28.8, rootSize[0] / 51.2 * 1.2);
		double borderRadius = Math.min(rootSize[1] / 72, rootSize[0] / 128 * 1.2);
		double borderWidth = Math.min(rootSize[1] / 240, rootSize[0] / 426.66);
		button.setStyle(
				"-fx-font-size: " + fontSize + "; -fx-graphic-text-gap: " + graphicTextGap + "; -fx-background-radius: "
						+ borderRadius + "; -fx-border-radius: " + borderRadius + "; -fx-border-width: " + borderWidth);
	}
}
