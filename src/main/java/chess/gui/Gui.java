package chess.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Starting point of the JavaFX GUI
 */
public class Gui extends Application {

	/**
	 * Enumeration of available scenes
	 */
	public enum ChessScene {
		Menu, Settings, NetworkConnection, Game;
	}

	private static Scene scene;

	/**
	 * This method is called by the Application to start the GUI.
	 *
	 * @param stage The initial root stage of the application.
	 */
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MenuView.fxml"));
			scene = new Scene(root);

			// set icon of the application
			stage.getIcons().add(new Image(Gui.class.getResourceAsStream("/chess/gui/images/icons/logo.png")));

			// activate the menuScene (MenuView) as default entry scene
			stage.setScene(scene);
			stage.show();
			double titleBarHeight = stage.getHeight() - stage.getScene().getHeight();
			stage.setMinWidth(640);
			stage.setMinHeight(360 + titleBarHeight);
			stage.setTitle("Chess");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Switches the current scene to the new scene
	 * 
	 * @param toScene new scene to switch to
	 */
	public static void switchTo(ChessScene toScene) {
		try {
			Parent root;
			switch (toScene) {
				case Menu:
					root = FXMLLoader.load(Gui.class.getResource("MenuView.fxml"));
					break;
				case Settings:
					root = FXMLLoader.load(Gui.class.getResource("SettingsView.fxml"));
					break;
				case NetworkConnection:
					root = FXMLLoader.load(Gui.class.getResource("NetworkConnectionView.fxml"));
					break;
				case Game:
					root = FXMLLoader.load(Gui.class.getResource("GameView.fxml"));
					break;
				default:
					root = FXMLLoader.load(Gui.class.getResource("MenuView.fxml"));
					break;
			}

			scene.setRoot(root);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The entry point of the GUI application.
	 *
	 * @param args The command line arguments passed to the application
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
