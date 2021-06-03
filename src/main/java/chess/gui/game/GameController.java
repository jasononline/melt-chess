package chess.gui.game;

import chess.gui.Gui;
import chess.gui.settings.SettingsModel;
import chess.gui.util.TextManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 * Controls behaviour of GUI elements except the chessboard (see
 * BoardController). The chess.model.Game class should be used as GameModel for
 * this scene.
 */
public class GameController {

	@FXML
	private Button settingsButton;
	@FXML
	private Button surrenderButton;
	@FXML
	private Button resetButton;
	@FXML
	private Button menuButton;

	@FXML
	private void initialize() {
		TextManager.computeText(settingsButton, "game.settings");
		TextManager.computeText(surrenderButton, "game.surrender");
		TextManager.computeText(resetButton, "game.reset");
		TextManager.computeText(menuButton, "game.menu");
	}

	@FXML
	private void handleButtonSettingsOnAction() {
		System.out.println("User clicked on Settings in GameView.");
		SettingsModel.setLastScene(SettingsModel.LastScene.Game);
		Gui.switchToSettings();
	}

	@FXML
	private void handleButtonSurrenderOnAction() {
		System.out.println("User clicked on Surrender in GameView");
	}

	@FXML
	private void handleButtonResetOnAction() {
		System.out.println("User clicked on Reset in GameView");
	}

	@FXML
	private void handleButtonMenueOnAction() {
		System.out.println("User clicked on Menue in GameView");
		Gui.switchToMenu();
	}

	@FXML
	private void handleCanvasOnMouseClicked(MouseEvent event) {
		System.out.println("User clicked on the Canvas in GameView");
		System.out.println(event.toString());
	}
}
