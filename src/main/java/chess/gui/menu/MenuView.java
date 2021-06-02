package chess.gui.menu;

/**
 * Defines look of the GUI. May be obsolete with the use of fxml?
 */
public class MenuView {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml1.fxml"));
    Parent root = (Parent)fxmlLoader.load();

    Scene scene = new Scene(root, 500, 500);
stage.setScene(scene);
stage.show();


}
