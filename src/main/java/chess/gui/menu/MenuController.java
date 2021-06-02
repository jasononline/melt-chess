package chess.gui.menu;

import chess.gui.Gui;
import chess.gui.settings.SettingsModel;
import javafx.fxml.FXML;

/**
 * Controls the behaviour and actions of the UI elements in the menu scene,
 * mainly switching to one of the other scenes and selecting game options.
 */
public class MenuController {

    /**
     * Stores the chosen enemy: 1 = PC, 2 = PVP, 3 = NetworkPVP, 0 = not chosen yet.
     */
    private int enemyChoice = 0;

    /**
     * Stroes the chosen color: true = white, false = black, null = not chosen yet.
     */
    private boolean color;


    /**
     * Controls the effect when clicking the radioButtonAI.
     * The enemChoice variable will be set by this function.
     */
    @FXML
    private void handleRadioButtonAIOnAction() {
        System.out.println("User clicked on Game against AI Button in the Main Menu");
        this.enemyChoice = 1;
    }


    /**
     * Controls the effect when clicking the radioButtonPVP.
     * The enemChoice variable will be set by this function.
     */
    @FXML
    private void handleRadioButtonPVPOnAction() {
        System.out.println("User clicked on Game against PVP Button in the Main Menu");
        this.enemyChoice = 2;
    }


    /**
     * Controls the effect when clicking the radioButtonNet.
     * The enemChoice variable will be set by this function.
     */
    @FXML
    private void handleRadioButtonNetOnAction() {
        System.out.println("User clicked on Game against Network-PVP Button in the Main Menu");
        this.enemyChoice = 3;
    }


    /**
     * Controls the effect when clicking the radioButtonBlack.
     * The color variable will be set by this function.
     */
    @FXML
    private void handleRadioButtonColorBlackOnAction() {
        System.out.println("User clicked on choose black in the Main Menu");
        this.color = false;
    }


    /**
     * Controls the effect when clicking the radioButtonWhite.
     * The color variable will be set by this function.
     */
    @FXML
    private void handleRadioButtonColorWhiteOnAction() {
        System.out.println("User clicked on choose white in the Main Menu");
        this.color = true;
    }

    /**
     * Controls the effect when clicking the startButton.
     */
    @FXML
    private void handleButtonStartOnAction() {
        System.out.println("User clicked on start in the Main Menu");
        // TODO start a new Game
        Gui.switchToGame();
    }


    /**
     * Controls the effect when clicking the settingsButton.
     */
    @FXML
    private void handleButtonSettingsOnAction() {
        System.out.println("User clicked on settings in the Main Menu");
        SettingsModel.setLastSceneName(SettingsModel.LastScene.Menu);
        Gui.switchToSettings();
    }

}
