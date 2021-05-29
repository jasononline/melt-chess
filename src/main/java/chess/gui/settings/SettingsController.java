package chess.gui.settings;

import javafx.fxml.FXML;

/**
 * Controls the behaviour and actions of the UI elements in the settings scene
 */
public class SettingsController {

    @FXML
    private void handleBtnSaveOnAction() {
        // TODO set all new values in the settings model
        System.out.println("User clicked on Save Button");
    }

    @FXML
    private void handleCheckboxFlipBoardOnAction() {
        System.out.println("User clicked on Flip-Rule Checkbox");
        SettingsModel.setFlipBoard(!SettingsModel.isFlipBoard());
        if(SettingsModel.isFlipBoard()) {
            System.out.println("Flip is now activated");
        } else {
            System.out.println("Flip is now deactivated");
        }
    }
}
