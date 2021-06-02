package chess.gui.game;

import chess.gui.Gui;
import javafx.fxml.FXML;

/**
 * Controls behaviour of GUI elements except the chessboard (see BoardController).
 * The chess.model.Game class should be used as GameModel for this scene.
 */
public class GameController {

    @FXML
    private void handleButtonSettingsOnAction() {
        System.out.println("User clicked on Settings in GameView.");
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
}
