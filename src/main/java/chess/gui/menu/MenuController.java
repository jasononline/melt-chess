package chess.gui.menu;

import chess.gui.Gui;
import chess.gui.game.GameModel;
import chess.util.networkServices.InitServerService;
import chess.gui.settings.SettingsModel;
import chess.gui.util.ResizeManager;
import chess.util.TextManager;
import chess.util.Server;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * Controls the behaviour and actions of the UI elements in the menu scene,
 * mainly switching to one of the other scenes and selecting game options.
 */
@SuppressWarnings({ "PMD.UnusedPrivateMethod", "PMD.TooManyFields" })
// Some methods in this class seem unused but they are used by FXML
// This class controls many elements of the gui, hence many fields are needed
// here.
public class MenuController {

	@FXML
	public AnchorPane rootPane;
	@FXML
	public Label titleLabel;
	@FXML
	public Label modeLabel;
	@FXML
	public Button playerModeButton;
	@FXML
	public Button aiModeButton;
	@FXML
	public Button networkModeButton;
	@FXML
	public AnchorPane colorPane;
	@FXML
	public Label colorLabel;
	@FXML
	public Button whiteColorButton;
	@FXML
	public Button blackColorButton;
	@FXML
	public Button startButton;
	@FXML
	public Button settingsButton;
	@FXML
	public Button quitButton;

	private static String selected = "selected";

	private ResizeManager resizeManager = new ResizeManager(this);

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
			resizeManager.resizeMenu(rootPane.getWidth(), rootPane.getHeight());
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
		playerModeButton.getStyleClass().remove(selected);
		aiModeButton.getStyleClass().remove(selected);
		networkModeButton.getStyleClass().remove(selected);
		colorPane.setDisable(true);
		source.getStyleClass().add(selected);

		if (source.equals(playerModeButton)) {
			whiteColorButton.getStyleClass().remove(selected);
			blackColorButton.getStyleClass().remove(selected);
			GameModel.setColor(GameModel.ChessColor.None);
			GameModel.setGameMode(GameModel.ChessMode.Player);
		}
		if (source.equals(aiModeButton)) {
			colorPane.setDisable(false);
			GameModel.setGameMode(GameModel.ChessMode.Computer);
		}
		if (source.equals(networkModeButton)) {
			whiteColorButton.getStyleClass().remove(selected);
			blackColorButton.getStyleClass().remove(selected);
			GameModel.setColor(GameModel.ChessColor.None);
			GameModel.setGameMode(GameModel.ChessMode.Network);
		}

		if (GameModel.getGameMode() != GameModel.ChessMode.None
				&& GameModel.getGameMode() != GameModel.ChessMode.Computer) {
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
		whiteColorButton.getStyleClass().remove(selected);
		blackColorButton.getStyleClass().remove(selected);
		source.getStyleClass().add(selected);

		if (source.equals(whiteColorButton))
			GameModel.setColor(GameModel.ChessColor.White);
		if (source.equals(blackColorButton))
			GameModel.setColor(GameModel.ChessColor.Black);

		if (GameModel.getColor() != GameModel.ChessColor.None) {
			startButton.setDisable(false);
		}
	}

	/**
	 * Controls the effect when clicking one of the menu buttons.
	 */
	@FXML
	private void handleMenuButtonOnAction(ActionEvent event) throws IOException {
		Node source = (Node) event.getSource();

		if (source.equals(startButton)) {
			if (GameModel.getGameMode() == GameModel.ChessMode.Network) {
				Server.quit();
				InitServerService server = new InitServerService();
				server.start();
				Gui.switchTo(Gui.ChessScene.NetworkConnection);
			} else {
				GameModel.beginNewGame();
				Gui.switchTo(Gui.ChessScene.Game);
			}

		}

		if (source.equals(settingsButton)) {
			SettingsModel.setLastScene(Gui.ChessScene.Menu);
			Gui.switchTo(Gui.ChessScene.Settings);
		}

		if (source.equals(quitButton))
			System.exit(0);
	}

}
