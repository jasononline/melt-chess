package chess.gui.settings;

import chess.gui.util.GraphicsManager;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

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

            // 3 lines of garbage code for testing (can be deleted)
            ImageView a = GraphicsManager.getGraphicAsImageView("Tobias-Chess-Symbols-Marking");
            ImageView b = GraphicsManager.getGraphicAsImageView("Tobias-Chess-Symbols-Marking", 10, 20);
            System.out.println("a.x = " + a.getX() + "\nb.x = " + b.getX());

        } else {
            System.out.println("Flip is now deactivated");
        }
    }
}
